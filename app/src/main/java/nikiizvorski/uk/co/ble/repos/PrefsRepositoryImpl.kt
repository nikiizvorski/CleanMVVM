package nikiizvorski.uk.co.ble.repos

import android.view.View
import androidx.lifecycle.MutableLiveData
import io.realm.Realm
import nikiizvorski.uk.co.ble.db.AppDAO
import nikiizvorski.uk.co.ble.pojos.Photos
import nikiizvorski.uk.co.ble.pojos.PhotoModel
import javax.inject.Inject

/**
 *
 * @property appDao AppDAO
 * @constructor
 */
class PrefsRepositoryImpl @Inject constructor(private val appDao: AppDAO, private val realm: Realm): PrefsRepository {
    private var visibility: MutableLiveData<Int> = MutableLiveData()
    var data: List<Photos> = listOf()


    /**
     *
     * @param data MutableLiveData<List<Device>>
     */
    override fun getDbRealmList(): List<Photos> {
        realm.executeTransaction { realm ->
            var deviceModel = findInRealm(realm, 101)

            if (deviceModel != null) {
                deviceModel.id = 101
                deviceModel.userId = 111
                deviceModel.title = "Samsung S10+"
                deviceModel.body = "Their shitty system doesn't work!"

//                data = listOf(Photo(deviceModel.userId, deviceModel.id, deviceModel.title, deviceModel.body.length))
                visibility.value = View.GONE
            } else {
                deviceModel = realm.createObject(PhotoModel::class.java)
                deviceModel.id = 101
                deviceModel.userId = 111
                deviceModel.title = "Samsung S10+"
                deviceModel.body = "Their shitty system doesn't work!"

//                data = listOf(Photo(deviceModel.userId, deviceModel.id, deviceModel.title, deviceModel.body.length))
                visibility.value = View.GONE
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
    override fun getDbList(): List<Photos>{
        return appDao.all
    }

    /**
     * Find in realm user model.
     *
     * @param realm the realm
     * @param id    the id
     * @return the user model
     */
    fun findInRealm(realm: Realm, id: Int): PhotoModel? {
        return realm.where(PhotoModel::class.java).equalTo("id", id).findFirst()
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
    override fun getVisibilityUpdate(): MutableLiveData<Int> {
        return visibility
    }
}