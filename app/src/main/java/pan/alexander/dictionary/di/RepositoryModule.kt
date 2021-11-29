package pan.alexander.dictionary.di

import dagger.Binds
import dagger.Module
import pan.alexander.dictionary.data.remote.RemoteRepositoryImpl
import pan.alexander.dictionary.domain.RemoteRepository

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideRemoteRepository(remoteRepository: RemoteRepositoryImpl): RemoteRepository
}
