package nikiizvorski.uk.co.ble.repos

import android.arch.lifecycle.MutableLiveData
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import nikiizvorski.uk.co.ble.api.AppService
import nikiizvorski.uk.co.ble.db.AppDAO
import nikiizvorski.uk.co.ble.pojos.Device
import timber.log.Timber
import javax.inject.Inject

/**
 *
 * @property appDao AppDAO
 * @property deviceService AppService
 * @property subscription Disposable
 * @constructor
 */
class RepositoryImpl @Inject constructor(private val appDao: AppDAO, private val deviceService: AppService) : Repository {
    private lateinit var subscription: Disposable

    /**
     *
     * @param id Int
     */
    override fun deleteSingleItemWithId(id: Int) {
        appDao.deleteSingleWithId(id)
    }

    /**
     *
     * @param data MutableLiveData<List<Device>>
     */
    override fun getDbList(data: MutableLiveData<List<Device>>) {
        data.value = appDao.all
    }

    /**
     *
     * @param data MutableLiveData<List<Device>>
     * @param visibility MutableLiveData<Int>
     */
    override fun getNetworkList(data: MutableLiveData<List<Device>>, visibility: MutableLiveData<Int>) {
        if (appDao.all.isEmpty()) {

            subscription = Observable.fromCallable { appDao.all }
                .flatMap {
                        dbPostList ->
                    if(dbPostList.isEmpty())
                        deviceService.getPosts().flatMap {
                                apiPostList ->  run {
                                appDao.insertAll(apiPostList)
                            Observable.just(apiPostList)
                            }
                        }
                    else
                        Observable.just(dbPostList)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { visibility.value = View.VISIBLE }
                .doOnTerminate { visibility.value = View.GONE }
                .subscribe({ result -> data.value = result }, { visibility.value = View.VISIBLE })

        } else {
            visibility.value = View.GONE
            data.value = appDao.all
        }
    }
}