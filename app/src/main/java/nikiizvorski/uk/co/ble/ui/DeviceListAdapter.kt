package nikiizvorski.uk.co.ble.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import nikiizvorski.uk.co.ble.R
import nikiizvorski.uk.co.ble.pojos.Device

/**
 *
 * @property devices ArrayList<Device>
 */
class DeviceListAdapter: RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {
    var devices: ArrayList<Device> = ArrayList()

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
        holder.bind(devices.get(position))
    }

    /**
     *
     * @return Int
     */
    override fun getItemCount(): Int = devices.size

    /**
     *
     * @param list List<Device>
     */
    fun updateData(list: List<Device>) {
        devices.clear()
        devices.addAll(list)
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

        fun bind(device: Device) {
            postTitle?.text = device.title
            postBody?.text = device.id.toString()
        }
    }
}