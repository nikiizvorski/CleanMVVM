package nikiizvorski.uk.co.ble.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nikiizvorski.uk.co.ble.pojos.Device
import nikiizvorski.uk.co.ble.repos.PrefsRepository
import nikiizvorski.uk.co.ble.repos.Repository
import javax.inject.Inject

/**
 *
 *
 * Make sure to have a proper encapsulation on your fields.
 * If you need make duplicate just to pass the data to the view
 * don't change the value of your data from the view directly.
 *
 * @property repository Repository
 * @property subscription Disposable
 * @property data MutableLiveData<List<Device>>
 * @property visibility MutableLiveData<Int>
 * @constructor
 */
class DeviceListViewModel @Inject constructor(private val repository: Repository, private val prefsRepository: PrefsRepository):
    ViewModel(){
    private lateinit var subscription: Disposable
    val data: MutableLiveData<List<Device>> = MutableLiveData()

    /**
     * Proper encapsulation example
     */
    val _visibility = MutableLiveData<Int>()
    val visibility: LiveData<Int>
        get() = _visibility


    init{
        loadDevices()
    }

    fun loadDevices(){
        viewModelScope.launch(Dispatchers.Main) {
            prefsRepository.getDbRealmList(data, _visibility)
            repository.executeManager()
        }
    }

    fun addItems(){
        loadDevices()
    }

    fun changeVisibility(value: Int) {
        _visibility.value = value
    }

    override fun onCleared() {
        super.onCleared()
        prefsRepository.closeRealm()
        subscription.dispose()
    }
}