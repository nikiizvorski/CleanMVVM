package nikiizvorski.uk.co.ble.db

import androidx.room.*
import nikiizvorski.uk.co.ble.pojos.Photos

@Dao
interface AppDAO {

    /**
     *
     * @param photos Array<out Photo>
     */
    @Insert
    fun insertOne(vararg photos: Photos)

    /**
     *
     * @param photos List<Photo>
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAll(photos: List<Photos>)

    /**
     * get all photos by order
     */
    @get:Query("SELECT * FROM photos ORDER BY page DESC")
    val all: List<Photos>

    /**
     *
     * @param theId Int
     * @return Array<Photo>
     */
    @Query("SELECT * FROM photos WHERE page > :theId")
    fun loadUsersWithId(theId: Int): Array<Photos>

    /**
     *
     * @param id Int
     */
    @Query("DELETE FROM photos WHERE page = :id")
    fun deleteSingleWithId(id: Int)

    /**
     *
     * @param photos Array<out Photo>
     */
    @Update
    fun updateDevices(vararg photos: Photos)

    /**
     * delete all devices
     */
    @Query("DELETE FROM photos")
    fun deleteAllFromDevices()
}