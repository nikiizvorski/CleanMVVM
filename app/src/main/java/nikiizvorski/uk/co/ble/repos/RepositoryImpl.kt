package nikiizvorski.uk.co.ble.repos

import android.content.Context
import android.os.Environment
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import nikiizvorski.uk.co.ble.api.AppService
import nikiizvorski.uk.co.ble.db.AppDAO
import nikiizvorski.uk.co.ble.pojos.Device
import javax.inject.Inject
import androidx.work.NetworkType
import kotlinx.coroutines.delay
import nikiizvorski.uk.co.ble.util.DeviceWorker
import java.io.File
import java.io.FileInputStream


/**
 *
 * @property appDao AppDAO
 * @property deviceService AppService
 * @property subscription Disposable
 * @constructor
 */
class RepositoryImpl @Inject constructor(private val appDao: AppDAO, private val deviceService: AppService, private val workManager: WorkManager) : Repository {

    private lateinit var subscription: Disposable

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
    override fun getDbList(data: MutableLiveData<List<Device>>) {
        data.value = appDao.all
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

    /**
     *
     * @return List<Device>
     */
    override suspend fun getListDevices(): List<Device> {
        // heavy work here
        delay(2000)

        return listOf(Device(109, 109, "STEST", "SBEST"))
    }
}