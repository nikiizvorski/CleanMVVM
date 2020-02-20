package nikiizvorski.uk.co.ble.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import nikiizvorski.uk.co.ble.pojos.Device

/**
 * Deal with networking
 */
interface NetworkRepository {

    /**
     *
     * @return MutableLiveData<List<Device>>
     */
    fun getNetworkList(): MutableLiveData<List<Device>>

    /**
     *
     * @return MutableLiveData<List<Device>>
     */
    suspend fun getNewNetworkList(): MutableLiveData<List<Device>>

    /**
     *
     * @return List<Device>?
     */
    suspend fun getCorrectNetworkList(): List<Device>?

    /**
     *
     * @return MutableLiveData<List<Device>>
     */
    fun getNetworkData(): LiveData<List<Device>>

    /**
     *
     * @return Int?
     */
    fun getVisibilityUpdate(): MutableLiveData<Int>
}