package nikiizvorski.uk.co.ble.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import nikiizvorski.uk.co.ble.pojos.Device
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
class DeviceListViewModel @Inject constructor(private val repository: Repository, private val prefsRepository: PrefsRepository):ViewModel(){
    private lateinit var subscription: Disposable
    val data = MutableLiveData<List<Device>>()

    /**
     * Proper encapsulation example
     */
    val _visibility = MutableLiveData<Int>()
    val visibility: LiveData<Int>
        get() = _visibility


    init{
        loadPosts()
    }

    fun loadPosts(){
        prefsRepository.getDbRealmList(data, _visibility)
        //repository.getNetworkList(data, _visibility)
        repository.executeManager()
    }

    fun exampleClick(){
        Timber.d("Clicked")
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun changeVisibility(value: Int) {
        _visibility.value = value
    }
}