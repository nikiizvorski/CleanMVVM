package nikiizvorski.uk.co.ble.api

import io.reactivex.Observable
import nikiizvorski.uk.co.ble.pojos.Device
import retrofit2.Response
import retrofit2.http.GET

interface AppService {

    /**
     * Get the list of the pots from the API
     */
    @GET("/posts")
    fun getPosts(): Observable<List<Device>>

    /**
     * Get the list of the pots from the API
     */
    @GET("/posts")
    suspend fun getNewPosts(): Response<List<Device>>
}