package nikiizvorski.uk.co.ble.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import androidx.navigation.fragment.NavHostFragment
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import nikiizvorski.uk.co.ble.R
import nikiizvorski.uk.co.ble.factory.AppViewModelFactory
import kotlinx.android.synthetic.main.activity_post_list.*
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
    val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(DeviceListViewModel::class.java) }
    var deviceListAdapter: DeviceListAdapter = DeviceListAdapter(this)
    var deviceRealmList: DeviceRealmListAdapter? = null

    /**
     *
     * @param savedInstanceState Bundle?
     */
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)
        AndroidInjection.inject(this)
        initUI()
    }

    /**
     * Init UI
     */
    private fun initUI() {

        /**
         * Nav Host Example and setup
         *
         * This line can be deleted but its here for the example
         */
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigation_host_fragment) as NavHostFragment?

        /**
         * Normal Adapter Setup
         */
        post_list.layoutManager = GridLayoutManager(this, 2)
        post_list.adapter = deviceListAdapter

        /**
         * Realm Setup
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