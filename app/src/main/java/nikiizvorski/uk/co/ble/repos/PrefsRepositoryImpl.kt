package nikiizvorski.uk.co.ble.repos

import android.view.View
import androidx.lifecycle.MutableLiveData
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nikiizvorski.uk.co.ble.db.AppDAO
import nikiizvorski.uk.co.ble.pojos.Device
import nikiizvorski.uk.co.ble.pojos.DeviceModel
import javax.inject.Inject

/**
 *
 * @property appDao AppDAO
 * @constructor
 */
class PrefsRepositoryImpl @Inject constructor(private val appDao: AppDAO, private val realm: Realm): PrefsRepository {
    private var visibility: Int = 0
    var data: List<Device> = listOf()


    /**
     *
     * @param data MutableLiveData<List<Device>>
     */
    override fun getDbRealmList(): List<Device> {
        realm.executeTransaction { realm ->
            var deviceModel = findInRealm(realm, 101)

            if (deviceModel != null) {
                deviceModel.id = 101
                deviceModel.userId = 111
                deviceModel.title = "Samsung S10+"
                deviceModel.body = "Their shity system doesn't work!"

                data = listOf(Device(deviceModel.userId, deviceModel.id, deviceModel.title, deviceModel.body))
                visibility = View.GONE
            } else {
                deviceModel = realm.createObject(DeviceModel::class.java)
                deviceModel.id = 101
                deviceModel.userId = 111
                deviceModel.title = "Samsung S10+"
                deviceModel.body = "Their shity system doesn't work!"

                data = listOf(Device(deviceModel.userId, deviceModel.id, deviceModel.title, deviceModel.body))
                visibility = View.GONE
            }
        }

        return data
    }

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
    override fun getDbList(): List<Device>{
        return appDao.all
    }

    /**
     * Find in realm user model.
     *
     * @param realm the realm
     * @param id    the id
     * @return the user model
     */
    fun findInRealm(realm: Realm, id: Int): DeviceModel? {
        return realm.where(DeviceModel::class.java).equalTo("id", id).findFirst()
    }

    /**
     * Close Instance
     */
    override fun closeRealm() {
        realm.close()
    }

    /**
     *
     * @return Int?
     */
    override fun getVisibilityUpdate(): Int? {
        return visibility
    }
}