package klt.mdy.caching.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import klt.mdy.caching.MovieDatabase
import klt.mdy.caching.data.repo.RepositoryImpl
import klt.mdy.caching.domain.repo.Repository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideRepository(
        client: HttpClient,
        movieDatabase: MovieDatabase,
        @Qualifier.Io io: CoroutineDispatcher,
    ): Repository {
        return RepositoryImpl(
            client = client,
            io = io,
            movieDb = movieDatabase
        )
    }
}