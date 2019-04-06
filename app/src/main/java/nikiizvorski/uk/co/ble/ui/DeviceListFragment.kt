package nikiizvorski.uk.co.ble.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import nikiizvorski.uk.co.ble.R
import nikiizvorski.uk.co.ble.factory.AppViewModelFactory
import javax.inject.Inject

/**
 *
 * @property viewModelFactory AppViewModelFactory
 * @property viewModel DeviceListViewModel
 */
class DeviceListFragment : DaggerFragment() {
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
        return inflater.inflate(R.layout.device_fragment, container, false)
    }

    /**
     *
     * @param view View
     * @param savedInstanceState Bundle?
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        viewModel.visibility.observe(this, Observer { visibility ->

        })
    }
}