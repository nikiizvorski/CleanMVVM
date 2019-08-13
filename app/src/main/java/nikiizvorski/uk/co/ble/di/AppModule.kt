package nikiizvorski.uk.co.ble.di

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import nikiizvorski.uk.co.ble.api.AppService
import nikiizvorski.uk.co.ble.db.AppDB
import nikiizvorski.uk.co.ble.db.AppDAO
import nikiizvorski.uk.co.ble.ext.BASE_URL
import nikiizvorski.uk.co.ble.repos.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    /**
     *
     * @return OkHttpClient
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    /**
     *
     * @return Realm
     */
    @Provides
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }

    /**
     *
     * @param okHttpClient OkHttpClient
     * @return Retrofit
     */
    @Provides
    @Singleton
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
    @Provides
    @Singleton
    fun providePostService(@NonNull retrofit: Retrofit): AppService {
        return retrofit.create(AppService::class.java)
    }

    /**
     *
     * @param application Application
     * @return AppDB
     */
    @Provides
    @Singleton
    fun provideDatabase(@NonNull application: Application): AppDB {
        return Room.databaseBuilder(application.applicationContext, AppDB::class.java, "postove.db").allowMainThreadQueries().build()
    }

    /**
     *
     * @param database AppDB
     * @return AppDAO
     */
    @Provides
    @Singleton
    fun provideHistoryDao(@NonNull database: AppDB): AppDAO {
        return database.postDao()
    }

    /**
     *
     * @return WorkManager
     */
    @Provides
    @Singleton
    fun providesWorkerManager(): WorkManager{
        return WorkManager.getInstance()
    }

    /**
     *
     * @param service AppService
     * @param dao AppDAO
     * @return Repository
     */
    @Provides
    @Singleton
    fun provideRepository(@NonNull service: AppService, @NonNull dao: AppDAO, @NonNull workManager: WorkManager): Repository {
        return RepositoryImpl(dao, service, workManager)
    }

    /**
     *
     * @param service AppService
     * @return NetworkRepository
     */
    @Provides
    @Singleton
    fun provideNetworkRepository(@NonNull service: AppService): NetworkRepository {
        return NetworkRepositoryImpl(service)
    }

    /**
     *
     * @param dao AppDAO
     * @return PrefsRepository
     */
    @Provides
    @Singleton
    fun providePrefsRepository(@NonNull dao: AppDAO, @NonNull realm: Realm): PrefsRepository {
        return PrefsRepositoryImpl(dao, realm)
    }
}