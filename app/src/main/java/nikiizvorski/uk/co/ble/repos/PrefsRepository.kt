package nikiizvorski.uk.co.ble.repos

import androidx.lifecycle.MutableLiveData
import nikiizvorski.uk.co.ble.pojos.Photos

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
    fun getDbRealmList(): List<Photos>

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
    fun getDbList(): List<Photos>
}