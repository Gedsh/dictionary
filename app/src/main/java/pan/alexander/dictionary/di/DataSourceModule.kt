package pan.alexander.dictionary.di

import dagger.Binds
import dagger.Module
import pan.alexander.dictionary.data.remote.RemoteDataSource
import pan.alexander.dictionary.data.remote.RemoteDataSourceImpl

@Module
abstract class DataSourceModule {

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource
}
