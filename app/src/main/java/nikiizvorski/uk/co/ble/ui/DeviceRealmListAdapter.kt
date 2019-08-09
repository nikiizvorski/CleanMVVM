package nikiizvorski.uk.co.ble.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import nikiizvorski.uk.co.ble.R
import nikiizvorski.uk.co.ble.pojos.Device
import nikiizvorski.uk.co.ble.pojos.DeviceModel

/**
 *
 * @property devices ArrayList<Device>
 *
 * You can also pass the ViewModel to the adapter directly there wouldn't be any problems with that also.
 * Garbage collector will finish its job without any memory leaks.
 */
class DeviceRealmListAdapter(private val collection: OrderedRealmCollection<DeviceModel>?, autoUpdate: Boolean):
    RealmRecyclerViewAdapter<DeviceModel, DeviceRealmListAdapter.ViewHolder>(collection, autoUpdate) {

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
    fun updateData(list: List<Device>) {
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
        private var postBody: TextView? = null

        init {
            postTitle = itemView.findViewById(R.id.post_title)
            postBody = itemView.findViewById(R.id.post_body)
        }

        fun bind(device: DeviceModel) {
            postTitle?.text = device.title
            postBody?.text = device.id.toString()
        }
    }
}