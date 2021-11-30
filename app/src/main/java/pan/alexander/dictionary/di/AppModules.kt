package pan.alexander.dictionary.di

import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pan.alexander.dictionary.BuildConfig
import pan.alexander.dictionary.data.network.NetworkRepositoryImpl
import pan.alexander.dictionary.data.remote.RemoteDataSource
import pan.alexander.dictionary.data.remote.RemoteDataSourceImpl
import pan.alexander.dictionary.data.remote.RemoteRepositoryImpl
import pan.alexander.dictionary.domain.NetworkRepository
import pan.alexander.dictionary.domain.RemoteRepository
import pan.alexander.dictionary.domain.TranslationInteractor
import pan.alexander.dictionary.domain.TranslationInteractorImpl
import pan.alexander.dictionary.ui.translation.TranslationViewModel
import pan.alexander.dictionary.utils.configuration.ConfigurationManager
import pan.alexander.dictionary.utils.configuration.ConfigurationManagerImpl
import pan.alexander.dictionary.utils.network.NetworkUtils
import pan.alexander.dictionary.utils.coroutines.DispatcherProvider
import pan.alexander.dictionary.utils.coroutines.DispatcherProviderImpl
import pan.alexander.dictionary.web.SkyEngApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CALL_TIMEOUT_SEC = 5L

object AppModules {

    @ExperimentalCoroutinesApi
    val vmModule = module {
        viewModel {
            TranslationViewModel(interactor = get())
        }
    }

    val interactorModule = module {
        factory<TranslationInteractor> {
            TranslationInteractorImpl(
                remoteRepository = get(),
                networkRepository = get()
            )
        }
    }

    val repoModule = module {

        single<RemoteRepository> {
            RemoteRepositoryImpl(
                remoteDataSource = get(),
                retrofit = get()
            )
        }

        single<NetworkRepository> {
            NetworkRepositoryImpl(networkUtils = get())
        }
    }

    val dataSourceModule = module {
        factory<RemoteDataSource> {
            RemoteDataSourceImpl(
                skyEngApi = get(),
                dispatcherProvider = get()
            )
        }
    }

    val utilModule = module {

        single<DispatcherProvider> {
            DispatcherProviderImpl()
        }

        single {
            NetworkUtils(androidContext())
        }

        factory<ConfigurationManager> {
            ConfigurationManagerImpl()
        }
    }

    val retrofitModule = module {

        single {
            get<Retrofit>().create(SkyEngApi::class.java)
        }

        single<Retrofit> {
            Retrofit.Builder()
                .baseUrl(get<ConfigurationManager>().getBaseUrl())
                .addConverterFactory(get<GsonConverterFactory>())
                .client(get())
                .build()
        }

        single<GsonConverterFactory> {
            GsonConverterFactory.create()
        }

        single {
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        }

        single {
            OkHttpClient.Builder()
                .callTimeout(CALL_TIMEOUT_SEC, TimeUnit.SECONDS).apply {
                    if (BuildConfig.DEBUG) {
                        addInterceptor(get<HttpLoggingInterceptor>())
                    }
                }.build()
        }
    }
}
