package pan.alexander.dictionary.di

import dagger.Binds
import dagger.Module
import pan.alexander.dictionary.domain.TranslationInteractor
import pan.alexander.dictionary.domain.TranslationInteractorImpl

@Module
abstract class InteractorModule {

    @Binds
    abstract fun provideTranslationInteractor(
        translationInteractor: TranslationInteractorImpl
    ): TranslationInteractor
}
