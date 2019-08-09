package nikiizvorski.uk.co.ble.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

/**
 * Add Lifecycle from Activity or Fragment
 *
 * @constructor
 */
class DeviceTest(lifecycle: Lifecycle) : LifecycleObserver {

    init {
        lifecycle.addObserver(this)
    }

    /**
     * Lifecycle OnStart Event Activation
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun deviceTestStart(){
        Timber.d("Device Test Catch Started.")
    }

    /**
     * Lifecycle OnStop Event Activation
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun deviceTestStop(){
        Timber.d("Device Test Catch Stopped.")
    }
}