package nikiizvorski.uk.co.ble.app

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.realm.Realm
import io.realm.RealmConfiguration
import nikiizvorski.uk.co.ble.di.DaggerAppComponent
import timber.log.Timber

@Suppress("unused")
class App: DaggerApplication() {
    private val appComponent = DaggerAppComponent.builder()
        .application(this)
        .build()

    override fun onCreate() {
        super.onCreate()

        // inject component
        appComponent.inject(this)

        // plant timber
        Timber.plant(Timber.DebugTree())

        // add Realm
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder().name("newdemo").schemaVersion(0).build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }
}