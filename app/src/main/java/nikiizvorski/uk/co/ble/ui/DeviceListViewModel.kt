package nikiizvorski.uk.co.ble.ui

import android.content.Context
import androidx.lifecycle.*
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import nikiizvorski.uk.co.ble.pojos.Device
import nikiizvorski.uk.co.ble.repos.NetworkRepository
import nikiizvorski.uk.co.ble.repos.PrefsRepository
import nikiizvorski.uk.co.ble.repos.Repository
import timber.log.Timber
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
class DeviceListViewModel @Inject constructor(private val repository: Repository, private val prefsRepository: PrefsRepository,
                                              private val networkRepository: NetworkRepository, private val context: Context
):
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
        workWithFile()
    }

    /**
     * Write/Read File it is not a good practise to add context in the ViewModel but this is the option for the example
     */
    private fun workWithFile() {
        viewModelScope.launch(Dispatchers.IO) {
            /**
             * Write to file
             */
            val written: Deferred<Boolean> = async { repository.writeToFile(context, "mhm") }
            Timber.d("File Written: " + written.await())

            /**
             * Read from file
             */
            val readFile: Deferred<String> = async { repository.readFromFile(context) }
            Timber.d("File Written: " + readFile.await())
        }
    }

    /**
     * Read File
     */

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
     * Add manual to test the rest of the methods
     */
    fun addItems(){
       getWebItems()
    }

    /**
     * Get Items from the Web
     */
    fun getWebItems(){
        viewModelScope.launch(Dispatchers.Main) {
            networkRepository.getNewNetworkList(data, _visibility)
        }
    }

    /**
     *
     * @param value Int
     */
    fun changeVisibility(value: Int) {
        _visibility.value = value
    }

    /**
     * Clear ViewModel
     */
    override fun onCleared() {
        super.onCleared()
        prefsRepository.closeRealm()
        subscription.dispose()
    }
}