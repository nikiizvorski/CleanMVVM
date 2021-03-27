package nikiizvorski.uk.co.ble.ui

import android.view.View
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import nikiizvorski.uk.co.ble.pojos.Photo
import nikiizvorski.uk.co.ble.pojos.Photos
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
 * Refer to this gist for examples of this ViewModel
 *
 * https://gist.github.com/nikiizvorski/9c2dfa7ac499eabc2e488280db84e4a1
 *
 * @property repository Repository
 * @property data MutableLiveData<List<Device>>
 * @property visibility MutableLiveData<Int>
 * @constructor
 */
@HiltViewModel
class PhotoListViewModel
@Inject constructor(private val repository: Repository, private val prefsRepository: PrefsRepository, private val networkRepository: NetworkRepository): ViewModel(){

    var data: MediatorLiveData<List<Photo>> = MediatorLiveData()
    /**
     * Proper encapsulation example
     */
    private val _visibility = MutableLiveData<Int>()
    val visibility: LiveData<Int>
        get() = _visibility

    /**
     * Change the method for different load
     *
     * loadDevices()
     * getWebItemsRetry()
     * workWithFile()
     * transformWebItems()
     */
    init{
        getWebItems()
    }

    /**
     * Get Items from the Web
     */
    fun getWebItems(){
        viewModelScope.launch(Dispatchers.Main) {
            data.addSource(networkRepository.getNetworkData()){
                data.value = it

                _visibility.value = View.INVISIBLE
            }
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