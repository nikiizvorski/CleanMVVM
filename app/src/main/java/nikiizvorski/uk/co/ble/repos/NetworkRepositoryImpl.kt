package nikiizvorski.uk.co.ble.repos

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import nikiizvorski.uk.co.ble.api.AppService
import nikiizvorski.uk.co.ble.ext.APP_KEY
import nikiizvorski.uk.co.ble.pojos.Photo
import nikiizvorski.uk.co.ble.pojos.Photos
import timber.log.Timber
import java.io.IOException
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
    val mData = MutableLiveData<List<Photo>>()
    private var visibility: MutableLiveData<Int> = MutableLiveData()

    init {
        visibility.value = View.VISIBLE
    }


    /**
     *
     * Added Automatic Retry to Disposable
     *
     * @param data MutableLiveData<List<Device>>
     * @param visibility MutableLiveData<Int>
     */
    override fun getNetworkList(): MutableLiveData<List<Photo>> {
        subscription = deviceService.getPhotos(APP_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retryWhen { throwables -> throwables.delay(1, TimeUnit.SECONDS) }
            .doOnSubscribe { visibility.value = View.VISIBLE }
            .doOnTerminate { visibility.value = View.GONE }
            .subscribe({ result -> mData.value = result.photos }, { visibility.value = View.VISIBLE })
        return mData
    }

    /**
     *
     * @param data MutableLiveData<List<Device>>
     * @param visibility MutableLiveData<Int>
     */
    override suspend fun getNewNetworkList():  MutableLiveData<List<Photo>> {
        withContext(Dispatchers.Main) {
            val response = retryIO { deviceService.getNewPhotos(APP_KEY) }
            if (response.isSuccessful) {

                    visibility.value = View.GONE
                    //mData.value = response.body()
                } else {
                    visibility.value = View.VISIBLE
                }
        }

        return mData
    }

    /**
     *
     * @return List<Device>?
     */
    override suspend fun getCorrectNetworkList(): List<Photo> {
        val response = retryIO { deviceService.getNewPhotos(APP_KEY) }
        return response.body()!!.photos
    }

    /**
     *
     * @return MutableLiveData<List<Device>>
     */
    override fun getNetworkData(): LiveData<List<Photo>> {
        subscription = deviceService.getPhotos(APP_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retryWhen { throwables -> throwables.delay(1, TimeUnit.SECONDS) }
            .doOnSubscribe { visibility.value = View.VISIBLE }
            .doOnTerminate { visibility.value = View.GONE }
            .subscribe({ result -> mData.value = result.photos
                visibility.value = View.GONE
                Timber.d("Executed2")} , {  })

        Timber.d("Executed3")
        return mData
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

    /**
     *
     * @return Int?
     */
    override fun getVisibilityUpdate(): MutableLiveData<Int> {
        return visibility
    }

    /**
     *
     * @return Flow<List<Device>?>
     */
    override fun getNetworkFlow(): Flow<Photos?> {
        return flow {
            val response = retryIO { deviceService.getNewPhotos(APP_KEY) }
            if (response.isSuccessful) {
                visibility.value = View.GONE
                emit(response.body())
            } else {
                visibility.value = View.VISIBLE
            }
        }
    }

    /**
     * Please do integrate your own Call Adapter otherwise this won't work for you.
     *
     * @return Flow<List<Device>?>
     */
    override suspend fun getNetworkFlowMap(): Flow<Photos> {
        return retryIO { deviceService.getFlowPhotos(APP_KEY) }
    }

    /**
     * Example Method with Flow
     *
     * @return Flow<List<Device>>
     */
    override suspend fun getFlowMapped(): Flow<List<Photo>> {
        return flow{
            emit(retryIO {
                deviceService.getThePosts().map { modTitle(it.photos[0]) }
            })

            visibility.value = View.GONE
        }
    }

    /**
     *
     * @param it Device
     * @return Device
     */
    private fun modTitle(it: Photo): Photo {
        it.photographer = it.photographer + it.photographer_id
        return it
    }
}