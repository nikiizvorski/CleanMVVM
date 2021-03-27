package nikiizvorski.uk.co.ble.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nikiizvorski.uk.co.ble.R
import nikiizvorski.uk.co.ble.pojos.Photo

/**
 *
 * @property devices ArrayList<Device>
 *
 * You can also pass the ViewModel to the adapter directly there wouldn't be any problems with that also.
 * Garbage collector will finish its job without any memory leaks.
 *
 * Improving with DiffUtil might be depending on your situation. ListAdapter submitList() to pass the data
 * and after pass a callback to the constructor of the adapter implement the two methods. And comprate the objects
 * that should be it.
 */
class DeviceListAdapter(private val context: Context): ListAdapter<Photo, DeviceListAdapter.ViewHolder>(DeviceCallbackAdapter()) {
    var devices: ArrayList<Photo> = ArrayList()
    lateinit var reference: PhotoListActivity
    /**
     *
     * @param parent ViewGroup
     * @param viewType Int
     * @return ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        reference = context as PhotoListActivity
        return ViewHolder(inflater)
    }

    /**
     *
     * @param holder ViewHolder
     * @param position Int
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(devices.get(position), reference)
    }

    /**
     *
     * @return Int
     */
    override fun getItemCount(): Int = devices.size

    /**
     *
     * @param list List<Device> can be removed
     */
    fun updateData(list: List<Photo>) {
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
        private var postBody: ImageView? = null

        init {
            postTitle = itemView.findViewById(R.id.post_title)
            postBody = itemView.findViewById(R.id.imageView)
        }

        fun bind(device: Photo, reference: PhotoListActivity) {
            postTitle?.text = device.photographer
            Glide.with(reference).load(device.src.small).into(postBody!!)
            postTitle?.setOnClickListener {
                reference.onItem(postTitle!!.visibility)
            }
        }
    }
}

/**
 * Update to DiffUtil example for better performance and to remove updateData method with the heavy DataSetChanged.
 */
class DeviceCallbackAdapter : DiffUtil.ItemCallback<Photo>(){
    override fun areItemsTheSame(p0: Photo, p1: Photo): Boolean {
        return p0.photographer == p1.photographer
    }

    override fun areContentsTheSame(p0: Photo, p1: Photo): Boolean {
        return p0 == p1
    }

}