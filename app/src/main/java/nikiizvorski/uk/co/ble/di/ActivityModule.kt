package nikiizvorski.uk.co.ble.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nikiizvorski.uk.co.ble.ui.DeviceListActivity
import nikiizvorski.uk.co.ble.ui.DeviceListFragment
import nikiizvorski.uk.co.ble.ui.DeviceListFragmentTwo

@Suppress("unused")
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributeDeviceListActivity(): DeviceListActivity

    @ContributesAndroidInjector
    internal abstract fun contributeDeviceListFragment(): DeviceListFragment

    @ContributesAndroidInjector
    internal abstract fun contributeDeviceListFragmentTwo(): DeviceListFragmentTwo
}