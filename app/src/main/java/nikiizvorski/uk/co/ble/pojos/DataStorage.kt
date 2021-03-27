package nikiizvorski.uk.co.ble.pojos

import android.os.Parcelable
import androidx.room.*
import io.realm.RealmObject
import io.realm.internal.Keep
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
//@Parcelize
//@Entity
//data class Device(val userId: Int, @PrimaryKey val id: Int, var title: String, val body: String) : Parcelable

@Keep
@KeepMember
open class PhotoModel(
    var userId: Int = 0,
    @PrimaryKey var id: Int = 0,
    var title: String = "",
    var body: String = ""
) : RealmObject()

@Parcelize
@Entity
data class Photos(
    var next_page: String = "",
    var userId: Int = 0,
    @PrimaryKey
    var page: Int = 0,
    var per_page: Int = 0,
    @Ignore
    var photos: List<Photo> = emptyList(),
    var total_results: Int = 0
) : Parcelable

@Parcelize
@Entity
data class Photo(
    var id: Int,
    var avg_color: String,
    var height: Int,
    var liked: Boolean,
    var photographer: String,
    var photographer_id: Int,
    var photographer_url: String,
    @Ignore var src: Src,
    var url: String,
    var width: Int
) : Parcelable

@Parcelize
@Entity
data class Src(
    var landscape: String,
    var large: String,
    var large2x: String,
    var medium: String,
    @ColumnInfo(name = "pic_info") var original: String,
    var portrait: String,
    var small: String,
    var tiny: String
) : Parcelable




