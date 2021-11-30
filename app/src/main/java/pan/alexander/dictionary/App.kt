package pan.alexander.dictionary

import android.app.Application
import pan.alexander.dictionary.di.ApplicationComponent
import pan.alexander.dictionary.di.DaggerApplicationComponent

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

    lateinit var daggerComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        initDaggerComponent()
    }

    private fun initDaggerComponent() {
        daggerComponent = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
    }
}
