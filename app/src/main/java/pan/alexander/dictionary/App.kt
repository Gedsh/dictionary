package pan.alexander.dictionary

import android.app.Application
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import pan.alexander.dictionary.di.AppModules

@ExperimentalCoroutinesApi
class App : Application() {

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
                AppModules.retrofitModule,
                AppModules.roomModule
            )
        }
    }
}
