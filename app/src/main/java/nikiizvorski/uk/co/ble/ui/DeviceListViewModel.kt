package nikiizvorski.uk.co.ble.ui

import android.content.Context
import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
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
 * @property data MutableLiveData<List<Device>>
 * @property visibility MutableLiveData<Int>
 * @constructor
 */
class DeviceListViewModel @Inject constructor(private val repository: Repository, private val prefsRepository: PrefsRepository,
                                              private val networkRepository: NetworkRepository, private val context: Context
):
    ViewModel(){
    var data: MediatorLiveData<List<Device>> = MediatorLiveData()
    val dataDB: MutableLiveData<List<Device>> = MutableLiveData()
    val dataNetwork: MutableLiveData<List<Device>> = MutableLiveData()

    /**
     * Please use DeviceActivity Flow Collection to check the example
     */
    val dataFlow: Flow<List<Device>?> = networkRepository.getNetworkFlow()

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


    /**
     * Change the method for different load
     *
     * loadDevices()
     * getWebItemsRetry()
     * workWithFile()
     * transformWebItems()
     */
    init{
//        getWebItems()
//        getWebFlowItems()
//        getWebFlowCollection()
        getWebFlowCollectionMapped()

        networkRepository.getVisibilityUpdate().observeForever{
            _visibility.value = it
        }
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
     * We want to get the data from Realm here and update the visibility also
     * we shouldn't pass any values
     */
    fun loadDevices(){
        viewModelScope.launch(Dispatchers.Main) {
            data.value = prefsRepository.getDbRealmList()

            // observeForever is lifeCycleAware when you use it with ViewModelScope
            prefsRepository.getVisibilityUpdate().observeForever{
                _visibility.value = it
            }

            repository.executeManager()
        }
    }

    /**
     * Get Mediator Data / change data to MediatorLiveData in order to use the method and observe the changes
     *
     * When would you use it well imagine you have items that you want in ASC/DESC you can add different order like
     * and change the objects order simply. Or imagine that you have a singleton class and you want to observe other items.
     *
     * You would like to keep a single stream and changing only its value on the way
     */
    fun getMediatorData() {
        /*
        viewModelScope.launch(Dispatchers.Main) {
            prefsRepository.getDbRealmList(dataDB, _visibility)
            data.addSource(dataDB) {
                it?.let { data.value = it }
                Timber.d("WENT HERE")
            }
            networkRepository.getNewNetworkList(dataNetwork, _visibility)
            data.addSource(dataNetwork) {
                it?.let { data.value = it }
                Timber.d("WENT HERE")
            }
        }
        */
    }

    /**
     * Get Items from the Web
     */
    fun getWebItems(){
        viewModelScope.launch(Dispatchers.Main) {
            data.addSource(networkRepository.getNetworkData()){
                data.value = it
            }
        }
    }

    /**
     * Example of cold - stream with Flow
     * Get Items from the web using Flow and converting it to LiveData.
     * Flow is an alternative to RxJava2 but it is inside Kotlin and can be used
     * as an example if you are creating SDK for something and you don't want to use any other libraries
     * that may affect your clients builds and dependencies you may use it since it is straight out of the box
     *
     * If you want some more examples of Flow and more advanced operations using it please don't hesitate to ask me.
     */
    fun getWebFlowItems() {
        viewModelScope.launch(Dispatchers.Main) {
            data.addSource(networkRepository.getNetworkFlow().asLiveData()) {
                data.value = it
            }
        }
    }

    /**
     * Flow collection in the ViewModel as it should be
     *
     * Example Path UI -> ViewModel Collection and LiveData -> Repository Flow -> Data Source Flow
     */
    fun getWebFlowCollection() {
        viewModelScope.launch(Dispatchers.Main) {
            networkRepository.getNetworkFlow().collect{
                data.value = it
            }
        }
    }

    /**
     * Flow collection in the ViewModel as it should be
     *
     * Example Path UI -> ViewModel Collection and LiveData -> Repository Flow -> Data Source Flow
     */
    fun getWebFlowCollectionMapped() {
        viewModelScope.launch(Dispatchers.Main) {
            networkRepository.getFlowMapped().collect{
                data.value = it
            }
        }
    }

    /**
     * Functions in both map() and switchMap() run on the Main Thread, so no long running operations should be done there!
     *
     * They are the way to transform liveData objects not to actually represent one and not the place for ViewModel logic
     *
     * Transformations contains MediatorLiveData inside! As for reference
     */
    fun transformWebItems() {
            networkRepository.getNetworkData().observeForever {
                data.value = it
                _visibility.value = View.GONE
            }

        // sampl1
           Transformations.map(data) { list ->
               data.value = list
                list.forEach {
                    it.title = it.title + it.id
                }
            }.observeForever {
               Timber.d("Data: $it")
           }

        // sampl2
        Transformations.switchMap(data) {
            networkRepository.getNetworkData()
        }.observeForever {
            Timber.d("Data: $it")
            it.forEach {
                it.title = it.title + it.id
            }
            data.value = it
        }
    }

    /**
     * Get Items from web with Retry Coroutines
     *
     * setting liveData value should be on the main
     *
     * this is the correct way to do it instead of the rest you want to keep SOLID
     *
     * the rest are just examples of what you can do
     */
    fun getWebItemsRetry() {
        viewModelScope.launch(Dispatchers.Main) {
            //networkRepository.getNewNetworkList(data, _visibility)
            val items: List<Device>? = async(Dispatchers.IO) {
                networkRepository.getCorrectNetworkList()
            }.await()

            data.value = items
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
     * Reverse LiveData order
     */
    fun reverseOrder() {
        viewModelScope.launch(Dispatchers.Main) {
            data.value = data.value!!.reversed()
        }
    }

    /**
     * Clear ViewModel you don't need this method if you are only using viewModelScope it should
     * clear everything automatically! So in that order you have to be aware of the lifecycles you are using.
     */
    override fun onCleared() {
        super.onCleared()
        prefsRepository.closeRealm()
    }
}