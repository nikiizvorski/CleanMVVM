package nikiizvorski.uk.co.ble.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import nikiizvorski.uk.co.ble.R
import nikiizvorski.uk.co.ble.databinding.ActivityDeviceBinding
import timber.log.Timber


/**
 * Using ViewBindings updated
 *
 * @property viewModelFactory AppViewModelFactory
 * @property viewModel DeviceListViewModel
 * @property deviceListAdapter DeviceListAdapter
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PhotoListActivity: AppCompatActivity(), OnAdapterManagement {

    /**
     * Fix for leak and encapsulation example follow the commits
     * @param int Int
     */
    override fun onItem(int: Int) {
        viewModel.changeVisibility(int)
    }

    /**
    @Inject lateinit var realm: Realm
    var data: RealmResults<DeviceModel>? = null
    **/

    private val viewModel: PhotoListViewModel by viewModels()
    var deviceListAdapter: DeviceListAdapter = DeviceListAdapter(this)
    //var deviceRealmList: DeviceRealmListAdapter? = null
    private lateinit var binding: ActivityDeviceBinding
    private lateinit var navController: NavController

    /**
     *
     * @param savedInstanceState Bundle?
     */
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.navigation_host_fragment)
        initUI()

    }

    /**
     * Init UI
     */
    private fun initUI() {

        /**
         * Normal Adapter Setup
         */
        binding.list.layoutManager = GridLayoutManager(this, 2)
        binding.list.adapter = deviceListAdapter

        /**
         * Realm Setupg
        **/
        realmSetup()

        /**
         * Observe the LiveData from ViewModel
         */
        viewModel.visibility.observe(this, Observer { visibility ->
            binding.progressBar.visibility = visibility!!
        })

        /**
         * Observe the LiveData from ViewModel
         */
        viewModel.data.observe(this, Observer { data ->
            data?.let {
                Timber.d("Data Observed Size: %s", it.size)
                deviceListAdapter.devices.clear()
                deviceListAdapter.devices.addAll(it)
                deviceListAdapter.notifyDataSetChanged()
            }
        })

        /**
         * Collect Data from Flow once available
         */
        /*lifecycleScope.launch {
            viewModel.dataFlow.collect {
                deviceListAdapter.devices.clear()
                deviceListAdapter.devices.addAll(it!!)
                deviceListAdapter.notifyDataSetChanged()
            }
        } */
    }

    /**
     * Realm setup
     */
    private fun realmSetup() {
        /*
            realm.executeTransaction(Realm.Transaction { realm ->
                data = realm.where(DeviceModel::class.java).findAll()
            })

            post_list.adapter = DeviceRealmListAdapter(data, true)
        */
    }
}