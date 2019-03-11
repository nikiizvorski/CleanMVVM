package nikiizvorski.uk.co.ble.pojos

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import android.support.annotation.Keep
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.internal.KeepMember
import kotlinx.android.parcel.Parcelize

/**
 *
 * @property userId Int
 * @property id Int
 * @property title String
 * @property body String
 * @constructor
 */
@Parcelize
@Entity
data class Device(val userId: Int, @PrimaryKey val id: Int, val title: String, val body: String) : Parcelable

@Keep
@KeepMember
open class DeviceModel(var userId: Int = 0, @PrimaryKey var id: Int = 0, var title: String = "", var body: String = "") : RealmObject()
