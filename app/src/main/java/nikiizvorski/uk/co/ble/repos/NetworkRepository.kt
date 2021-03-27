package nikiizvorski.uk.co.ble.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import nikiizvorski.uk.co.ble.pojos.Photo
import nikiizvorski.uk.co.ble.pojos.Photos

/**
 * Deal with networking
 */
interface NetworkRepository {

    /**
     *
     * @return MutableLiveData<List<Device>>
     */
    fun getNetworkList(): MutableLiveData<List<Photo>>

    /**
     *
     * @return MutableLiveData<List<Device>>
     */
    suspend fun getNewNetworkList(): MutableLiveData<List<Photo>>

    /**
     *
     * @return List<Device>?
     */
    suspend fun getCorrectNetworkList(): List<Photo>

    /**
     *
     * @return MutableLiveData<List<Device>>
     */
    fun getNetworkData(): LiveData<List<Photo>>

    /**
     *
     * @return Int?
     */
    fun getVisibilityUpdate(): MutableLiveData<Int>

    /**
     *
     * @return Flow<List<Device>>
     */
    fun getNetworkFlow(): Flow<Photos?>

    /**
     *
     * @return Flow<List<Device>?>
     */
    suspend fun getNetworkFlowMap(): Flow<Photos>?

    /**
     *
     * @return Flow<List<Device>>
     */
    suspend fun getFlowMapped(): Flow<List<Photo>>
}