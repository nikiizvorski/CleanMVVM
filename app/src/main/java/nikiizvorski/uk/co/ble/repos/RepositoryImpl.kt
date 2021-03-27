package nikiizvorski.uk.co.ble.repos

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import nikiizvorski.uk.co.ble.api.AppService
import nikiizvorski.uk.co.ble.db.AppDAO
import nikiizvorski.uk.co.ble.pojos.Photos
import javax.inject.Inject
import androidx.work.NetworkType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.delay
import nikiizvorski.uk.co.ble.util.DeviceWorker


/**
 *
 * @property appDao AppDAO
 * @property deviceService AppService
 * @property subscription Disposable
 * @constructor
 */
class RepositoryImpl @Inject constructor(private val appDao: AppDAO, private val deviceService: AppService, private val workManager: WorkManager) : Repository {
    val mData = MutableLiveData<List<Photos>>()
    private lateinit var subscription: Disposable
    private var visibility: Int = 0

    /**
     *
     * @param id Int
     */
    override fun deleteSingleItemWithId(id: Int) {
        appDao.deleteSingleWithId(id)
    }

    /**
     * Write to file
     */
    override suspend fun writeToFile(context: Context, text: String): Boolean{
        context.openFileOutput("text.txt", Context.MODE_PRIVATE).use {
            it.write(text.toByteArray())
        }
        return true
    }

    /**
     *
     * @return String
     */
    override suspend fun readFromFile(context: Context) : String {
        return context.openFileInput("text.txt").use {
            it.readBytes().toString(Charsets.UTF_8)
        }
    }

    /**
     *
     * @param data MutableLiveData<List<Device>>
     */
    override fun getDbList(): MutableLiveData<List<Photos>> {
        mData.value = appDao.all
        return mData
    }

    /**
     * Executes the work manager
     */
    override fun executeManager() {
        // There are two types of request that you can use OneTime and Periodic here we make sure the network is there.
        val myConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequest.Builder(DeviceWorker::class.java)
            .setConstraints(myConstraints)
            .build()


        workManager.enqueue(request)
    }

    /**
     *
     * @return MutableLiveData<List<Device>>
     */
    override fun getNetworkList(): MutableLiveData<List<Photos>> {
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
                .doOnSubscribe { visibility = View.VISIBLE }
                .doOnTerminate { visibility = View.GONE }
                .subscribe({ result -> mData.value = result }, { visibility = View.VISIBLE })

        } else {
            visibility = View.GONE
            mData.value = appDao.all
        }

        return mData
    }

    /**
     *
     * @return List<Device>
     */
    override suspend fun getListDevices(): List<Photos> {
        // heavy work here
        delay(2000)
        //listOf(Photo(109, 109, 100.toString(), 100) todo
        return listOf()
    }

    /**
     *
     * @return Int?
     */
    override fun getVisibilityUpdate(): Int? {
        return visibility
    }
}