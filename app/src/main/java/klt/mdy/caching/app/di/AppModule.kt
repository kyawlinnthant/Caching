package klt.mdy.caching.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import klt.mdy.caching.data.repo.ApiDataSourceImpl
import klt.mdy.caching.domain.repo.ApiDataSource
import kotlinx.coroutines.CoroutineDispatcher
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    kotlinx.serialization.json.Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.tag("ktor.client").v(message = message)
                    }
                }
                level = LogLevel.ALL
            }
            install(ResponseObserver) {
                onResponse { response ->
                    Timber.tag("ktor.response")
                        .v(message = "value = ${response.status.value} : description = ${response.status.description}")
                }
            }
            install(DefaultRequest) {
                header(
                    key = HttpHeaders.ContentType,
                    value = ContentType.Application.Json
                )
            }
        }
    }

    @Provides
    @Singleton
    fun providesApiDataSource(
        client: HttpClient,
        @Qualifier.Io io: CoroutineDispatcher,
    ): ApiDataSource {
        return ApiDataSourceImpl(
            client = client,
            io = io,
        )
    }
}