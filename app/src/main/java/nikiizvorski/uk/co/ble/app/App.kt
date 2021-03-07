package nikiizvorski.uk.co.ble.app

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

@HiltAndroidApp
class App: Application() {

    /**
     * Setup
     */
    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupRealm()
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