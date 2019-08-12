package nikiizvorski.uk.co.ble.repos

import android.view.View
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import nikiizvorski.uk.co.ble.api.AppService
import nikiizvorski.uk.co.ble.pojos.Device
import timber.log.Timber
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
}