package nikiizvorski.uk.co.ble.app

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

/**
 * Application Class
 *
 * @property appComponent [ERROR : null type]
 */
@Suppress("unused")
class App: DaggerApplication() {
    private val appComponent = DaggerAppComponent.builder()
        .application(this)
        .build()

    /**
     * Setup
     */
    override fun onCreate() {
        super.onCreate()
        injectDagger()
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

    /**
     * Setup Dagger
     */
    private fun injectDagger() {
        appComponent.inject(this)
    }

    /**
     * App Injector Setup
     * @return AndroidInjector<out DaggerApplication>
     */
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }
}