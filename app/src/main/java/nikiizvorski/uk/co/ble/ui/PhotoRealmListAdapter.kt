package nikiizvorski.uk.co.ble.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import nikiizvorski.uk.co.ble.R
import nikiizvorski.uk.co.ble.pojos.PhotoModel
import nikiizvorski.uk.co.ble.pojos.Photo

/**
 *
 * @property photos ArrayList<Photos>
 *
 * You can also pass the ViewModel to the adapter directly there wouldn't be any problems with that also.
 * Garbage collector will finish its job without any memory leaks.
 */
class PhotoRealmListAdapter(private val collection: OrderedRealmCollection<PhotoModel>?, autoUpdate: Boolean):
    RealmRecyclerViewAdapter<PhotoModel, PhotoRealmListAdapter.ViewHolder>(collection, autoUpdate) {

    /**
     *
     * @param parent ViewGroup
     * @param viewType Int
     * @return ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(inflater)
    }

    /**
     *
     * @param holder ViewHolder
     * @param position Int
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (collection != null) {
            holder.bind(collection.get(position))
        }
    }

    /**
     *
     * @return Int
     */
    override fun getItemCount(): Int = collection!!.size

    /**
     *
     * @param list List<Device>
     */
    fun updateData(list: List<PhotoModel>) {
        this.notifyDataSetChanged()
    }

    /**
     *
     * @property postTitle TextView?
     * @property postBody TextView?
     * @constructor
     */
    class ViewHolder(inflater: View): RecyclerView.ViewHolder(inflater) {
        private var postTitle: TextView? = null
        private var postBody: ImageView? = null

        init {
            postTitle = itemView.findViewById(R.id.post_title)
            postBody = itemView.findViewById(R.id.imageView)
        }

        fun bind(device: PhotoModel) {
            postTitle?.text = device.title
        }
    }
}