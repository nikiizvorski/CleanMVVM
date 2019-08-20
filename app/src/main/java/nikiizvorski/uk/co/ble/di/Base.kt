package nikiizvorski.uk.co.ble.di

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import androidx.work.WorkManager
import io.realm.Realm
import nikiizvorski.uk.co.ble.api.AppService
import nikiizvorski.uk.co.ble.db.AppDAO
import nikiizvorski.uk.co.ble.db.AppDB
import org.koin.androidx.viewmodel.dsl.viewModel
import nikiizvorski.uk.co.ble.repos.*
import nikiizvorski.uk.co.ble.ui.*
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL: String = "https://jsonplaceholder.typicode.com"

val module: Module = module {
    single<Repository> { RepositoryImpl() }
    single<PrefsRepository> { PrefsRepositoryImpl() }
    single<NetworkRepository> { NetworkRepositoryImpl() }
    factory { provideOkHttpClient() }
    factory { provideRetrofit(get()) }
    factory { providePostService(get()) }
    factory { provideRealm() }
    factory { provideDatabase(get()) }
    factory { provideHistoryDao(get()) }
    factory { providesWorkerManager() }

    /**
     * https://github.com/InsertKoinIO/koin/issues/506
     *
     * Current Issue with Koin doesn't work with ViewModelScope and LiveData properly
     * waiting until fix for a proper integration.
     *
     * That is why Dagger is a more stable and currently best implementation it is supported out of the box.
     */

    viewModel { DeviceListViewModel() }
}

/**
 *
 * @return OkHttpClient
 */
fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .build()
}

/**
 *
 * @return Realm
 */
fun provideRealm(): Realm {
    return Realm.getDefaultInstance()
}

/**
 *
 * @param okHttpClient OkHttpClient
 * @return Retrofit
 */
fun provideRetrofit(@NonNull okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

/**
 *
 * @param retrofit Retrofit
 * @return AppService
 */
fun providePostService(@NonNull retrofit: Retrofit): AppService {
    return retrofit.create(AppService::class.java)
}

/**
 *
 * @param application Application
 * @return AppDB
 */
fun provideDatabase(@NonNull application: Application): AppDB {
    return Room.databaseBuilder(application.applicationContext, AppDB::class.java, "postove.db").allowMainThreadQueries().build()
}

/**
 *
 * @param database AppDB
 * @return AppDAO
 */
fun provideHistoryDao(@NonNull database: AppDB): AppDAO {
    return database.postDao()
}

/**
 *
 * @return WorkManager
 */
fun providesWorkerManager(): WorkManager {
    return WorkManager.getInstance()
}