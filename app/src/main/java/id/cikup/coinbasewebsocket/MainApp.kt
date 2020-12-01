package id.cikup.coinbasewebsocket

import android.app.Application
import id.cikup.coinbasewebsocket.di.myAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()
            // use the Android context given there
            androidContext(this@MainApp)
            // module list
            modules(myAppModule)
        }
    }
}