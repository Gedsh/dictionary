package pan.alexander.dictionary.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import pan.alexander.dictionary.ui.translation.TranslationFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [InteractorModule::class, RepositoryModule::class, DataSourceModule::class,
        UtilsModule::class, RetrofitModule::class]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(appContext: Context): Builder
        fun build(): ApplicationComponent
    }

    fun inject(translationFragment: TranslationFragment)
}
