package pan.alexander.dictionary.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pan.alexander.dictionary.ui.translation.TranslationViewModel
import pan.alexander.dictionary.ui.viewmodel.ViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun provideViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TranslationViewModel::class)
    abstract fun provideTranslationViewModel(
        translationViewModel: TranslationViewModel
    ): ViewModel
}
