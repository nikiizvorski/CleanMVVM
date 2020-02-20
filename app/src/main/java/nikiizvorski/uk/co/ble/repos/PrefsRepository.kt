package nikiizvorski.uk.co.ble.repos

import androidx.lifecycle.MutableLiveData
import nikiizvorski.uk.co.ble.pojos.Device

/**
 * Deal with DB
 */
interface PrefsRepository {


    /**
     *
     * @param id Int
     */
    fun deleteSingleItemWithId(id: Int)

    /**
     *
     * @param data MutableLiveData<List<Device>>
     */
    fun getDbRealmList(): List<Device>

    /**
     * Close Realm
     */
    fun closeRealm()

    /**
     *
     * @return Int?
     */
    fun getVisibilityUpdate(): MutableLiveData<Int>

    /**
     *
     * @return List<Device>
     */
    fun getDbList(): List<Device>
}