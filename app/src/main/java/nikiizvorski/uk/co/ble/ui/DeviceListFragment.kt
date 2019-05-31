package nikiizvorski.uk.co.ble.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import nikiizvorski.uk.co.ble.R
import nikiizvorski.uk.co.ble.databinding.DeviceFragmentBinding
import nikiizvorski.uk.co.ble.factory.AppViewModelFactory
import nikiizvorski.uk.co.ble.util.DeviceTest
import javax.inject.Inject

/**
 *
 * Using DataBinding for Views on the Activity for Example
 *
 * @property viewModelFactory AppViewModelFactory
 * @property viewModel DeviceListViewModel
 */
class DeviceListFragment : DaggerFragment() {
    private lateinit var deviceTest: DeviceTest
    private lateinit var binding: DeviceFragmentBinding
    /**
     * Updated the Fragment and Activity Shared View Model base adjust to requirement
     */
    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    val viewModel by lazy {  activity?.run {
        ViewModelProviders.of(this, viewModelFactory).get(DeviceListViewModel::class.java)
    } ?: throw Exception("Invalid Activity") }

    /**
     *
     * @param context Context
     */
    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    /**
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup?
     * @param savedInstanceState Bundle?
     * @return View?
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.device_fragment, container, false)

        /**
         * Set the ViewModel directly in the view with binding.deviceViewModel = viewModel and use the methods straight away
         * and after use the methods directly i can't recommend that but that's on a personal decision of the way and structure
         * your project is going to have.
         *
         * Also you can directly set the lifecycle with binding.setLifecycleOwner(this) to make the data
         * lifecycle aware also.
         *
         */

        return binding.root
    }

    /**
     *
     * @param view View
     * @param savedInstanceState Bundle?
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deviceTest = DeviceTest(this.lifecycle)
        initUI()
    }

    private fun initUI() {
        viewModel.visibility.observe(this, Observer { visibility ->
            binding.offlineLayout.visibility = visibility!!
        })
    }
}