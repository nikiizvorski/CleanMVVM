package nikiizvorski.uk.co.ble.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_post_list.*
import nikiizvorski.uk.co.ble.R
import nikiizvorski.uk.co.ble.databinding.ActivityPostListBinding
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
class DeviceListActivity: AppCompatActivity(), OnAdapterManagement {

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
    val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(DeviceListViewModel::class.java) }
    var deviceListAdapter: DeviceListAdapter = DeviceListAdapter(this)
    var deviceRealmList: DeviceRealmListAdapter? = null
    private lateinit var binding: ActivityPostListBinding
            private lateinit var navController: NavController

    /**
     *
     * @param savedInstanceState Bundle?
     */
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_list)
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

        /**
         * Realm Setup
        **/
        realmSetup()

        /**
         * Observe the LiveData from ViewModel
         */
        viewModel.visibility.observe(this, Observer { visibility ->
            //progressBar.visibility = visibility!!
        })

        /**
         * Observe the LiveData from ViewModel
         */
        viewModel.data.observe(this, Observer { data ->
            data?.let {
                Timber.d("Data Observed Size: %s", it.size)
                deviceListAdapter.devices.addAll(it)
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