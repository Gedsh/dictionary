package pan.alexander.dictionary.di

import android.util.Log
import androidx.room.Room
import coil.ImageLoader
import coil.util.CoilUtils
import coil.util.DebugLogger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pan.alexander.dictionary.BuildConfig
import pan.alexander.dictionary.data.local.LocalDataSource
import pan.alexander.dictionary.data.local.LocalDataSourceImpl
import pan.alexander.dictionary.data.local.LocalRepositoryImpl
import pan.alexander.dictionary.data.network.NetworkRepositoryImpl
import pan.alexander.dictionary.data.remote.RemoteDataSource
import pan.alexander.dictionary.data.remote.RemoteDataSourceImpl
import pan.alexander.dictionary.data.remote.RemoteRepositoryImpl
import pan.alexander.core_db.database.AppDatabase
import pan.alexander.core_db.database.LongListTypeConverter
import pan.alexander.core_utils.configuration.ConfigurationManager
import pan.alexander.core_utils.configuration.ConfigurationManagerImpl
import pan.alexander.core_utils.coroutines.DispatcherProvider
import pan.alexander.core_utils.coroutines.DispatcherProviderImpl
import pan.alexander.core_utils.network.NetworkUtils
import pan.alexander.dictionary.domain.*
import pan.alexander.dictionary.domain.translation.TranslationInteractor
import pan.alexander.dictionary.domain.translation.TranslationInteractorImpl
import pan.alexander.core_web.web.SkyEngApi
import pan.alexander.dictionary.domain.history.HistoryInteractor
import pan.alexander.dictionary.domain.history.HistoryInteractorImpl
import pan.alexander.dictionary.test.EspressoIdlingResource
import pan.alexander.dictionary.ui.history.HistoryViewModel
import pan.alexander.dictionary.ui.translation.TranslationViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CALL_TIMEOUT_SEC = 5L

const val ACTIVITY_RETAINED_SCOPE = "pan.alexander.dictionary.ACTIVITY_RETAINED_SCOPE"

object AppModules {

    @ExperimentalCoroutinesApi
    val vmModule = module {
        viewModel {
            TranslationViewModel()
        }
        viewModel {
            HistoryViewModel()
        }
    }

    val interactorModule = module {

        scope(named(ACTIVITY_RETAINED_SCOPE)) {
            scoped<TranslationInteractor> {
                TranslationInteractorImpl(
                    localRepository = get(),
                    remoteRepository = get(),
                    networkRepository = get(),
                    dispatcherProvider = get()
                )
            }
        }

        scope(named(ACTIVITY_RETAINED_SCOPE)) {
            scoped<HistoryInteractor> {
                HistoryInteractorImpl(
                    localRepository = get()
                )
            }
        }

    }

    val repoModule = module {

        single<LocalRepository> {
            LocalRepositoryImpl(localDataSource = get())
        }

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

        factory<LocalDataSource> {
            LocalDataSourceImpl(
                searchResponseDao = get(),
                translationDao = get(),
                meaningDao = get(),
                historyDao = get()
            )
        }

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

        single {
            Retrofit.Builder()
                .baseUrl(get<ConfigurationManager>().getBaseUrl())
                .addConverterFactory(get<GsonConverterFactory>())
                .client(get())
                .build()
        }

        single {
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

    val roomModule = module {

        single {
            Room.databaseBuilder(
                androidContext(),
                AppDatabase::class.java,
                get<ConfigurationManager>().getAppDbName()
            ).addTypeConverter(LongListTypeConverter())
                .fallbackToDestructiveMigration()
                .build()
        }

        single {
            get<AppDatabase>().searchResponseDao()
        }

        single {
            get<AppDatabase>().translationDao()
        }

        single {
            get<AppDatabase>().meaningDao()
        }

        single {
            get<AppDatabase>().historyDao()
        }
    }

    val imageLoaderModule = module {
        single {
            ImageLoader.Builder(androidContext())
                .availableMemoryPercentage(0.25)
                .okHttpClient {
                    // Don't limit concurrent network requests by host.
                    val dispatcher = Dispatcher().apply { maxRequestsPerHost = maxRequests }

                    OkHttpClient.Builder()
                        .dispatcher(dispatcher)
                        .cache(CoilUtils.createDefaultCache(androidContext()))
                        .build()
                }.apply {
                    if (BuildConfig.DEBUG) {
                        logger(DebugLogger(Log.VERBOSE))
                    }
                }
                .build()
        }
    }

    val testModule = module {
        single {
            EspressoIdlingResource()
        }
    }
}
