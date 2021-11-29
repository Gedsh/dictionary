package pan.alexander.dictionary.di

import dagger.Binds
import dagger.Module
import pan.alexander.dictionary.utils.configuration.ConfigurationManager
import pan.alexander.dictionary.utils.configuration.ConfigurationManagerImpl
import pan.alexander.dictionary.utils.rx.SchedulerProvider
import pan.alexander.dictionary.utils.rx.SchedulerProviderImpl

@Module
abstract class UtilsModule {

    @Binds
    abstract fun provideRxSchedulers(
        schedulerProvider: SchedulerProviderImpl
    ): SchedulerProvider

    @Binds
    abstract fun provideConfigurationManager(
        configurationManager: ConfigurationManagerImpl
    ): ConfigurationManager
}
