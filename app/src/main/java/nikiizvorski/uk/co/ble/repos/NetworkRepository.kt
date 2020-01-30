package nikiizvorski.uk.co.ble.repos

import androidx.lifecycle.MutableLiveData
import nikiizvorski.uk.co.ble.pojos.Device

/**
 * Deal with networking
 */
interface NetworkRepository {

    /**
     *
     * @param data MutableLiveData<List<Device>>
     * @param visibility MutableLiveData<Int>
     */
    fun getNetworkList(data: MutableLiveData<List<Device>>, visibility: MutableLiveData<Int>)

    /**
     *
     * @param data MutableLiveData<List<Device>>
     * @param visibility MutableLiveData<Int>
     */
    suspend fun getNewNetworkList(data: MutableLiveData<List<Device>>, visibility: MutableLiveData<Int>)

    /**
     *
     * @return List<Device>?
     */
    suspend fun getCorrectNetworkList(): List<Device>?
}