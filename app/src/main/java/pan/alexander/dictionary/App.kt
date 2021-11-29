package pan.alexander.dictionary

import android.app.Application
import pan.alexander.dictionary.di.ApplicationComponent
import pan.alexander.dictionary.di.DaggerApplicationComponent

class App: Application() {
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
