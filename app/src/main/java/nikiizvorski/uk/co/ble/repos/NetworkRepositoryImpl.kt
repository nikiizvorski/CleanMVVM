package nikiizvorski.uk.co.ble.repos

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nikiizvorski.uk.co.ble.api.AppService
import nikiizvorski.uk.co.ble.pojos.Device
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import retrofit2.Retrofit
import timber.log.Timber

/**
 *
 * @property deviceService AppService
 * @property subscription Disposable
 * @constructor
 */
class NetworkRepositoryImpl : NetworkRepository, KoinComponent {
    private lateinit var subscription: Disposable
    private val retrofit: Retrofit by inject()
    private val deviceService: AppService = retrofit.create(AppService::class.java)

    /**
     *
     * @param data MutableLiveData<List<Device>>
     * @param visibility MutableLiveData<Int>
     */
    override fun getNetworkList(data: MutableLiveData<List<Device>>, visibility: MutableLiveData<Int>) {
        subscription = deviceService.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
        val response = deviceService.getNewPosts()
        withContext(Dispatchers.Main) {
            try {
                if (response.isSuccessful) {
                    Timber.d("Success")
                    visibility.value = View.GONE
                    data.value = response.body()
                } else {
                    visibility.value = View.VISIBLE
                }
            } catch (e: HttpException) {
                Timber.e("Exception ${e.message}")

            } catch (e: Throwable) {
                Timber.e("Something else went wrong")
            }
        }
    }
}