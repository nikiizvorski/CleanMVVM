package nikiizvorski.uk.co.ble.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import nikiizvorski.uk.co.ble.R
import nikiizvorski.uk.co.ble.databinding.DeviceFragmentBinding
import nikiizvorski.uk.co.ble.util.PhotoTest
import timber.log.Timber

/**
 *
 * Using DataBinding for Views on the Activity for Example
 *
 * @property viewModelFactory AppViewModelFactory
 * @property viewModel DeviceListViewModel
 */
@AndroidEntryPoint
class PhotoListFragment : Fragment() {
    private lateinit var deviceTest: PhotoTest
    private lateinit var binding: DeviceFragmentBinding
    /**
     * Updated the Fragment and Activity Shared View Model base adjust to requirement
     */
    private val viewModel by lazy {  activity?.run {
        ViewModelProvider(this).get(PhotoListViewModel::class.java)
    } ?: throw Exception("Invalid Activity") }

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
        binding = DeviceFragmentBinding.inflate(layoutInflater,  container, false)
        return binding.root
    }

    /**
     *
     * @param view View
     * @param savedInstanceState Bundle?
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deviceTest = PhotoTest(this.lifecycle)
        initUI()
    }

    private fun initUI() {
        /**
         * Reverse items
         *
         */
        binding.btn.setOnClickListener {
            viewModel.reverseOrder()
        }

        /**
         * Observer without the view logic
         */
        viewModel.visibility.observe(viewLifecycleOwner, Observer { visibility ->
            if (visibility == View.VISIBLE) {
                Timber.d("VISIBLE")
            } else {
                Timber.d("INVISIBLE")
            }
        })
    }
}