package nikiizvorski.uk.co.ble.repos

import android.arch.lifecycle.MutableLiveData
import nikiizvorski.uk.co.ble.pojos.Device

/**
 * Deal with networking
 */
interface NetworkRepository {

    /**
     *
     * @param data MutableLiveData<List<Device>>
     * @param visibility MutableLiveData<Int>
     */
    fun getNetworkList(data: MutableLiveData<List<Device>>, visibility: MutableLiveData<Int>)
}