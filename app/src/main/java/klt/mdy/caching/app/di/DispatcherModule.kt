package klt.mdy.caching.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    @Qualifier.Io
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}