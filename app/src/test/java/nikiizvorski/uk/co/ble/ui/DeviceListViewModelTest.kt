package nikiizvorski.uk.co.ble.ui

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import nikiizvorski.uk.co.ble.MainCoroutineRule
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

@ExperimentalCoroutinesApi
class DeviceListViewModelTest {
    val context = ApplicationProvider.getApplicationContext<Context>()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupDeviceListViewModel() {

    }

    @Test
    fun getData() {
    }

    @Test
    fun get_visibility() {
    }

    @Test
    fun getVisibility() {
    }

    @Test
    fun getDevices() {
    }

    @Test
    fun setDevices() {
    }

    @Test
    fun loadAsyncDevices() {
    }

    @Test
    fun loadItemsAsync() {
    }

    @Test
    fun loadDevices() {
    }

    @Test
    fun addItems() {
    }

    @Test
    fun getWebItems() {
    }

    @Test
    fun changeVisibility() {
    }

    @Test
    fun onCleared() {
    }
}