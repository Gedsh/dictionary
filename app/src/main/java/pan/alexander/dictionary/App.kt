package pan.alexander.dictionary

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import pan.alexander.dictionary.di.AppModules

class App : Application() {

    init {
        System.setProperty("rx3.purge-enabled", "false")
        System.setProperty("rx2.purge-enabled", "false")
        System.setProperty("rx3.computation-threads", "1")
        System.setProperty("rx2.computation-threads", "1")
    }

    companion object {
        const val LOG_TAG = "dictionary"
    }

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(
                AppModules.vmModule,
                AppModules.interactorModule,
                AppModules.repoModule,
                AppModules.dataSourceModule,
                AppModules.utilModule,
                AppModules.retrofitModule
            )
        }
    }
}
