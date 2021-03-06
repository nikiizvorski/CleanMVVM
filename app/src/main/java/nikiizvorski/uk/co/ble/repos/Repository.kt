package nikiizvorski.uk.co.ble.repos

import android.content.Context
import androidx.lifecycle.MutableLiveData
import nikiizvorski.uk.co.ble.pojos.Photos

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
     * @return MutableLiveData<List<Device>>
     */
    fun getDbList(): MutableLiveData<List<Photos>>

    /**
     *
     * @return MutableLiveData<List<Device>>
     */
    fun getNetworkList(): MutableLiveData<List<Photos>>

    /**
     *
     * @param id Int
     */
    fun deleteSingleItemWithId(id: Int)

    /**
     *
     * @return List<Device>
     */
    suspend fun getListDevices(): List<Photos>

    /**
     *
     * @param context Context
     * @param text String
     * @return Boolean
     */
    suspend fun writeToFile(context: Context, text: String): Boolean

    /**
     *
     * @param context Context
     * @return String
     */
    suspend fun readFromFile(context: Context): String

    /**
     *
     * @return Int?
     */
    fun getVisibilityUpdate(): Int?
}