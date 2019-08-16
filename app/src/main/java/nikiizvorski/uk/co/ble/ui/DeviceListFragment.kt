package nikiizvorski.uk.co.ble.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import nikiizvorski.uk.co.ble.R
import nikiizvorski.uk.co.ble.databinding.DeviceFragmentBinding
import nikiizvorski.uk.co.ble.util.DeviceTest
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 *
 * Using DataBinding for Views on the Activity for Example
 *
 * @property viewModelFactory AppViewModelFactory
 * @property viewModel DeviceListViewModel
 */
class DeviceListFragment : Fragment() {
    private lateinit var deviceTest: DeviceTest
    private lateinit var binding: DeviceFragmentBinding
    /**
     * Updated the Fragment and Activity Shared View Model base adjust to requirement
     */
    val viewModel: DeviceListViewModel by viewModel()

    /**
     *
     * @param context Context
     */
    override fun onAttach(context: Context) {
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
         *
         */
        NavHostFragment.findNavController(this)

        /**
        binding.btn.setOnClickListener {

            /**
             * You can use all the new lifecycleOwners options in activity, fragment etc
             * to perform the actions that are required with the certain scope.
             */

            /**
             * This will probably crash if you do it before the two seconds
             */
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)
                Toast.makeText(activity, "It will probably crash", Toast.LENGTH_LONG).show()
            }

            /**
             * This will proceed as it has to
             */
            viewLifecycleOwner.lifecycleScope.launch {
                delay(2000)
                Toast.makeText(activity, "It will be canceled", Toast.LENGTH_LONG).show()
            }

            viewModel.addItems()
        }
        */

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