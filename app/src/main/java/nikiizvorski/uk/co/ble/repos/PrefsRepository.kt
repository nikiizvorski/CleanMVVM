package nikiizvorski.uk.co.ble.repos

import androidx.lifecycle.MutableLiveData
import nikiizvorski.uk.co.ble.pojos.Device

/**
 * Deal with DB
 */
interface PrefsRepository {

    /**
     *
     * @param data MutableLiveData<List<Device>>
     */
    fun getDbList(data: MutableLiveData<List<Device>>)

    /**
     *
     * @param id Int
     */
    fun deleteSingleItemWithId(id: Int)

    /**
     *
     * @param data MutableLiveData<List<Device>>
     */
    fun getDbRealmList(
        data: MutableLiveData<List<Device>>,
        visibility: MutableLiveData<Int>
    )

    /**
     * Close Realm
     */
    fun closeRealm()
}