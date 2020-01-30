package nikiizvorski.uk.co.ble.repos

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import nikiizvorski.uk.co.ble.api.AppService
import nikiizvorski.uk.co.ble.pojos.Device
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 *
 * @property deviceService AppService
 * @property subscription Disposable
 * @constructor
 */
class NetworkRepositoryImpl @Inject constructor(private val deviceService: AppService): NetworkRepository {
    private lateinit var subscription: Disposable

    /**
     *
     * Added Automatic Retry to Disposable
     *
     * @param data MutableLiveData<List<Device>>
     * @param visibility MutableLiveData<Int>
     */
    override fun getNetworkList(data: MutableLiveData<List<Device>>, visibility: MutableLiveData<Int>) {
        subscription = deviceService.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retryWhen { throwables -> throwables.delay(1, TimeUnit.SECONDS) }
            .doOnSubscribe { visibility.value = View.VISIBLE }
            .doOnTerminate { visibility.value = View.GONE }
            .subscribe({ result -> data.value = result }, { visibility.value = View.VISIBLE })
    }

    /**
     *
     * @param data MutableLiveData<List<Device>>
     * @param visibility MutableLiveData<Int>
     */
    override suspend fun getNewNetworkList(data: MutableLiveData<List<Device>>, visibility: MutableLiveData<Int>) {
        withContext(Dispatchers.Main) {
            val response = retryIO { deviceService.getNewPosts() }
            if (response.isSuccessful) {
                    visibility.value = View.GONE
                    data.value = response.body()
                } else {
                    visibility.value = View.VISIBLE
                }
        }
    }

    /**
     *
     * @return List<Device>?
     */
    override suspend fun getCorrectNetworkList(): List<Device>? {
        val response = retryIO { deviceService.getNewPosts() }
        return response.body()
    }

    /**
     *
     * @param times Int
     * @param initialDelay Long
     * @param maxDelay Long
     * @param factor Double
     * @param block SuspendFunction0<T>
     * @return T
     */
    suspend fun <T> retryIO(
        times: Int = Int.MAX_VALUE,
        initialDelay: Long = 100, // 0.1 second
        maxDelay: Long = 1000,    // 1 second
        factor: Double = 2.0,
        block: suspend () -> T): T
    {
        var currentDelay = initialDelay
        repeat(times - 1) {
            try {
                return block()
            } catch (e: IOException) {
                Timber.e("Exception:" + e.cause)
            }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
        }
        return block() // last attempt
    }
}