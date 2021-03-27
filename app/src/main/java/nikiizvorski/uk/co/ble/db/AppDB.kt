package nikiizvorski.uk.co.ble.db

import androidx.room.Database
import androidx.room.RoomDatabase
import nikiizvorski.uk.co.ble.pojos.Photos

@Database(entities = [Photos::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    abstract fun postDao(): AppDAO
}