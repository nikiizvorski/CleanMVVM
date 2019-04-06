package nikiizvorski.uk.co.ble.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nikiizvorski.uk.co.ble.ui.DeviceListActivity
import nikiizvorski.uk.co.ble.ui.DeviceListFragment

@Suppress("unused")
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributeDeviceListActivity(): DeviceListActivity

    @ContributesAndroidInjector
    internal abstract fun contributeConnectivityFragment(): DeviceListFragment
}