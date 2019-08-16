package nikiizvorski.uk.co.ble.app

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * App Class
 */
class App: Application() {

    /**
     * Setup
     */
    override fun onCreate() {
        super.onCreate()
        injectKoin()
        setupTimber()
        setupRealm()
    }

    private fun injectKoin() {
        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()

            // use the Android context given there
            androidContext(this@App)

            // load properties from assets/koin.properties file
            androidFileProperties()

            // module list
            modules(nikiizvorski.uk.co.ble.di.module)
        }
    }

    /**
     * Setup Realm
     */
    private fun setupRealm() {
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder().name("newdemo").schemaVersion(0).build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }

    /**
     * Setup Timber
     */
    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }
}