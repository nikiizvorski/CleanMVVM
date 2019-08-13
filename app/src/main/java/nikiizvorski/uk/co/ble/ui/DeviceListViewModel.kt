package nikiizvorski.uk.co.ble.ui

import androidx.lifecycle.*
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nikiizvorski.uk.co.ble.pojos.Device
import nikiizvorski.uk.co.ble.repos.PrefsRepository
import nikiizvorski.uk.co.ble.repos.Repository
import okhttp3.Dispatcher
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

    /**
     * New Implementation
     */
    var devices = liveData(Dispatchers.IO) {
        val listDev = repository.getListDevices()
        emit(listDev)
    }

    /**
     * Old Implementation
     */
    fun loadAsyncDevices() {
        viewModelScope.launch {
            var emps: List<Device>? = null
            withContext(Dispatchers.IO) {
                emps = repository.getListDevices()
            }

            data.value = emps
        }
    }

    /**
     * Another Implementation of await and suspend
     */
    fun loadItemsAsync(){
        /**
         * You can remove async and await simply by adding withContext(Dispatchers.IO){}
         */
        viewModelScope.launch {
            val smallList = async(Dispatchers.IO) { repository.getListDevices() }
            data.value = smallList.await()
        }
    }


    init{
        loadDevices()
    }

    /**
     * Stable Implementation
     */
    fun loadDevices(){
        viewModelScope.launch(Dispatchers.Main) {
            prefsRepository.getDbRealmList(data, _visibility)
            repository.executeManager()
        }
    }

    /**
     * Add manual to test
     */
    fun addItems(){
//        loadDevices()

//        loadAsyncDevices()

        loadItemsAsync()
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