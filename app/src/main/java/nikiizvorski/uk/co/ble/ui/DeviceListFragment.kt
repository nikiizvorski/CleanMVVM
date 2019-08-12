package nikiizvorski.uk.co.ble.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import nikiizvorski.uk.co.ble.R
import nikiizvorski.uk.co.ble.databinding.DeviceFragmentBinding
import nikiizvorski.uk.co.ble.factory.AppViewModelFactory
import nikiizvorski.uk.co.ble.util.DeviceTest
import timber.log.Timber
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
        ViewModelProvider(this, viewModelFactory).get(DeviceListViewModel::class.java)
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
     * Example of usage of direct DataBinding and ViewModel and without. You can remove all of the code and replace that logic alone.
     * Depends on the company preference.
     *
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup?
     * @param savedInstanceState Bundle?
     * @return View?
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.device_fragment, container, false)
        binding.deviceListViewModel = viewModel
        binding.lifecycleOwner = this
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
        /**
         * Find Nav Controller in Fragment?
         */
        NavHostFragment.findNavController(this)

        /**
         * Observer without the view logic
         */
        viewModel.visibility.observe(this, Observer { visibility ->
            if (visibility == View.VISIBLE) {
                Timber.d("VISIBLE")
            } else {
                Timber.d("INVISIBLE")
            }
        })
    }
}