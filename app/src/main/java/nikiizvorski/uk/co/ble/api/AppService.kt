package nikiizvorski.uk.co.ble.api

import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import nikiizvorski.uk.co.ble.pojos.Photos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface AppService {


    /**
     * Get list of the people from the API
     */
    @GET("/v1/search?query=nature")
    fun getPhotos(@Header("Authorization") app_key: String?): Observable<Photos>

    @GET("/v1/search?query=nature")
    fun getFlowPhotos(@Header("Authorization") app_key: String?): Flow<Photos>

    @GET("/v1/search?query=nature")
    suspend fun getNewPhotos(@Header("Authorization") app_key: String?): Response<Photos>

    /**
     * Get the list of the pots from the API
     */
    @GET("/posts")
    fun getPosts(): Observable<List<Photos>>

    /**
     * Get the list of the pots from the API
     */
    @GET("/posts")
    fun getFlowPosts(): Flow<List<Photos>>

    /**
     * Get the list of the pots from the API
     */
    @GET("/posts")
    suspend fun getNewPosts(): Response<List<Photos>>

    /**
     * Get the list of the pots from the API
     */
    @GET("/posts")
    suspend fun getThePosts(): List<Photos>
}