package nikiizvorski.uk.co.ble.db

import androidx.room.*
import nikiizvorski.uk.co.ble.pojos.Device

@Dao
interface AppDAO {

    /**
     *
     * @param devices Array<out Device>
     */
    @Insert
    fun insertOne(vararg devices: Device)

    /**
     *
     * @param devices List<Device>
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAll(devices: List<Device>)

    /**
     * get all devices by order
     */
    @get:Query("SELECT * FROM device ORDER BY id DESC")
    val all: List<Device>

    /**
     *
     * @param theId Int
     * @return Array<Device>
     */
    @Query("SELECT * FROM device WHERE userId > :theId")
    fun loadUsersWithId(theId: Int): Array<Device>

    /**
     *
     * @param id Int
     */
    @Query("DELETE FROM device WHERE id = :id")
    fun deleteSingleWithId(id: Int)

    /**
     *
     * @param devices Array<out Device>
     */
    @Update
    fun updateDevices(vararg devices: Device)

    /**
     * delete all devices
     */
    @Query("DELETE FROM device")
    fun deleteAllFromDevices()
}