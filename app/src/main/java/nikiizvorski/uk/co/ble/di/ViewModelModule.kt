package nikiizvorski.uk.co.ble.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import nikiizvorski.uk.co.ble.factory.AppViewModelFactory
import nikiizvorski.uk.co.ble.ui.DeviceListViewModel


@Suppress("unused")
@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DeviceListViewModel::class)
    internal abstract fun bindMainActivityViewModel(mainActivityViewModel: DeviceListViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(appViewModelFactory: AppViewModelFactory): ViewModelProvider.Factory
}