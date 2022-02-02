package pan.alexander.dictionary

import android.app.Application
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import pan.alexander.dictionary.di.AppModules.dataSourceModule
import pan.alexander.dictionary.di.AppModules.imageLoaderModule
import pan.alexander.dictionary.di.AppModules.interactorModule
import pan.alexander.dictionary.di.AppModules.repoModule
import pan.alexander.dictionary.di.AppModules.retrofitModule
import pan.alexander.dictionary.di.AppModules.roomModule
import pan.alexander.dictionary.di.AppModules.testModule
import pan.alexander.dictionary.di.AppModules.utilModule
import pan.alexander.dictionary.di.AppModules.vmModule

@ExperimentalCoroutinesApi
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(
                vmModule,
                interactorModule,
                repoModule,
                dataSourceModule,
                utilModule,
                retrofitModule,
                roomModule,
                imageLoaderModule,
                testModule
            )
        }
    }
}
