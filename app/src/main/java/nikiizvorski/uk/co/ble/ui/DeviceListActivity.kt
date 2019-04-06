package nikiizvorski.uk.co.ble.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import dagger.android.AndroidInjection
import nikiizvorski.uk.co.ble.R
import nikiizvorski.uk.co.ble.factory.AppViewModelFactory
import kotlinx.android.synthetic.main.activity_post_list.*
import timber.log.Timber
import javax.inject.Inject

/**
 *
 * @property viewModelFactory AppViewModelFactory
 * @property viewModel DeviceListViewModel
 * @property deviceListAdapter DeviceListAdapter
 */
class DeviceListActivity: AppCompatActivity(), OnAdapterManagement {

    override fun onItem(int: Int) {
        viewModel.visibility.value = int
    }

    /**
    @Inject lateinit var realm: Realm
    var data: RealmResults<DeviceModel>? = null
    **/
    @Inject lateinit var viewModelFactory: AppViewModelFactory
    val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(DeviceListViewModel::class.java) }
    var deviceListAdapter: DeviceListAdapter = DeviceListAdapter(this)
    var deviceRealmList: DeviceRealmListAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)
        AndroidInjection.inject(this)
        initUI()
    }

    private fun initUI() {
        post_list.layoutManager = GridLayoutManager(this, 2)
        post_list.adapter = deviceListAdapter

        /**
        realm.executeTransaction(Realm.Transaction { realm ->
            data = realm.where(DeviceModel::class.java).findAll()
        })

        post_list.adapter = DeviceRealmListAdapter(this, data, true)
        **/

        viewModel.visibility.observe(this, Observer { visibility ->
            progressBar.visibility = visibility!!
        })

        viewModel.data.observe(this, Observer { data ->
            if (data != null) {
                Timber.d("Data Observed Size: %s", data.size)
                deviceListAdapter.updateData(data)
                deviceListAdapter.notifyDataSetChanged()
            }
        })
    }
}