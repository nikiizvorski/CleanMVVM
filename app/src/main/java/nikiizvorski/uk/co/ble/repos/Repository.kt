package nikiizvorski.uk.co.ble.repos

import android.arch.lifecycle.MutableLiveData
import nikiizvorski.uk.co.ble.pojos.Device

/**
 * Repository Main
 */
interface Repository {

    /**
     * Executes worker for testing
     */
    fun executeManager()

    /**
     *
     * @param data MutableLiveData<List<Device>>
     */
    fun getDbList(data: MutableLiveData<List<Device>>)

    /**
     *
     * @param data MutableLiveData<List<Device>>
     * @param visibility MutableLiveData<Int>
     */
    fun getNetworkList(data: MutableLiveData<List<Device>>, visibility: MutableLiveData<Int>)

    /**
     *
     * @param id Int
     */
    fun deleteSingleItemWithId(id: Int)
}