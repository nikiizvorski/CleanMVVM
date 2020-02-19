package nikiizvorski.uk.co.ble.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_device.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nikiizvorski.uk.co.ble.R
import nikiizvorski.uk.co.ble.databinding.ActivityDeviceBinding
import nikiizvorski.uk.co.ble.factory.AppViewModelFactory
import timber.log.Timber
import javax.inject.Inject

/**
 * Using Synthetic for Views on the Activity for Example
 *
 * @property viewModelFactory AppViewModelFactory
 * @property viewModel DeviceListViewModel
 * @property deviceListAdapter DeviceListAdapter
 */
class DeviceListActivity: DaggerAppCompatActivity(), OnAdapterManagement {

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
    @Inject lateinit var viewModelFactory: AppViewModelFactory
    val viewModel by lazy { ViewModelProvider(this, viewModelFactory).get(DeviceListViewModel::class.java) }
    var deviceListAdapter: DeviceListAdapter = DeviceListAdapter(this)
    var deviceRealmList: DeviceRealmListAdapter? = null
    private lateinit var binding: ActivityDeviceBinding
    private lateinit var navController: NavController

    /**
     *
     * @param savedInstanceState Bundle?
     */
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_device)
        binding.lifecycleOwner = this
        navController = Navigation.findNavController(this, R.id.navigation_host_fragment)
        AndroidInjection.inject(this)
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
            progressBar.visibility = visibility!!
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