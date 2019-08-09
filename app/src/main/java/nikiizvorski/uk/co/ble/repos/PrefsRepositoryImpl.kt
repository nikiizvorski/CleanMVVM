package nikiizvorski.uk.co.ble.repos

import android.view.View
import androidx.lifecycle.MutableLiveData
import io.realm.Realm
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

    /**
     *
     * @param data MutableLiveData<List<Device>>
     */
    override fun getDbRealmList(
        data: MutableLiveData<List<Device>>,
        visibility: MutableLiveData<Int>
    ) {
        realm.executeTransaction { realm ->
            var deviceModel = findInRealm(realm, 101)

            if (deviceModel != null) {
                deviceModel.id = 101
                deviceModel.userId = 111
                deviceModel.title = "Samsung S10+"
                deviceModel.body = "Their shity system doesn't work!"

                data.value = listOf(Device(deviceModel.userId, deviceModel.id, deviceModel.title, deviceModel.body))
                visibility.value = View.GONE
            } else {
                deviceModel = realm.createObject(DeviceModel::class.java)
                deviceModel.id = 101
                deviceModel.userId = 111
                deviceModel.title = "Samsung S10+"
                deviceModel.body = "Their shity system doesn't work!"

                data.value = listOf(Device(deviceModel.userId, deviceModel.id, deviceModel.title, deviceModel.body))
                visibility.value = View.GONE
            }
        }

        realm.close()
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
    override fun getDbList(data: MutableLiveData<List<Device>>) {
        data.value = appDao.all
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
}