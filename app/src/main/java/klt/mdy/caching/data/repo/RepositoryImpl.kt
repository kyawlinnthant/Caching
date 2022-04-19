package klt.mdy.caching.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import klt.mdy.caching.BuildConfig
import klt.mdy.caching.MovieDatabase
import klt.mdy.caching.app.common.Constant
import klt.mdy.caching.app.common.Endpoint
import klt.mdy.caching.app.common.Resource
import klt.mdy.caching.app.common.safeApiCall
import klt.mdy.caching.app.di.Qualifier
import klt.mdy.caching.data.model.ResponseDto
import klt.mdy.caching.domain.model.MovieVo
import klt.mdy.caching.domain.model.MovieWithIndexVo
import klt.mdy.caching.domain.repo.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import paging.moviedb.MovieEntity
import paging.moviedb.RemoteEntity
import javax.inject.Inject

@ExperimentalPagingApi
class RepositoryImpl @Inject constructor(
    private val client: HttpClient,
    private val movieDb: MovieDatabase,
    @Qualifier.Io private val io: CoroutineDispatcher,
) : Repository {

    private val movieQueries = movieDb.movieEntityQueries
    private val keyQueries = movieDb.remoteEntityQueries

    private val config = PagingConfig(
        pageSize = Constant.PAGE_SIZE,
        enablePlaceholders = false
    )

    override suspend fun getMovies(): Flow<Resource<List<MovieVo>>> {
        val request = Endpoint.BASE_URL
        return flow {
            emit(Resource.Loading())
            emit(
                safeApiCall(
                    response = client.request<ResponseDto>(request) {
                        method = HttpMethod.Get
                        parameter(
                            key = Endpoint.PARAMS_API_KEY,
                            value = BuildConfig.API_KEY
                        )
                    }.toVo()
                )
            )
        }.flowOn(io)
    }

    override suspend fun getMoviesFromNetwork(): Flow<PagingData<MovieWithIndexVo>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                PagingDataSource(
                    client = client
                )
            }
        ).flow
    }

    override suspend fun getMoviesFromDb(): Flow<PagingData<MovieWithIndexVo>> {
        val pagingSourceFactory = { movieQueries.getAllMovies() }
        //todo Paging and sqlDelight is in beta : I will apply this shit soon!
        /*return Pager(
            config = config,
            remoteMediator = RemoteMediatorDataSource(
                client = client,
                movieDb = movieDb
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow*/
        return Pager(
            config = config,
            pagingSourceFactory = {
                PagingDataSource(
                    client = client
                )
            }
        ).flow
    }

    override suspend fun getMovieById(id: Long): MovieEntity? {
        return withContext(io) {
            movieQueries.getMovieById(id = id).executeAsOneOrNull()
        }
    }

    override fun getAllMovies(): Flow<List<MovieEntity>> {
        return movieQueries.getAllMovies().asFlow().mapToList()
    }

    override suspend fun deleteMovieById(id: Long) {
        withContext(io) {
            movieQueries.deleteMovieById(id = id)
        }
    }

    override suspend fun insertMovie(
        id: Long?,
        imageUrl: String,
        posterUrl: String,
        overview: String,
        originalTitle: String,
        title: String,
        releasedDate: String,
        popularity: Double,
        voteCount: Double,
        voteAverage: Double
    ) {
        withContext(io) {
            movieQueries.insertMovie(
                id = id,
                imageUrl = imageUrl,
                posterUrl = posterUrl,
                overview = overview,
                originalTitle = originalTitle,
                title = title,
                releasedDate = releasedDate,
                popularity = popularity,
                voteCount = voteCount,
                voteAverage = voteAverage
            )
        }
    }

    override suspend fun getRemoteKeyById(id: Long): RemoteEntity? {
        return withContext(io) {
            keyQueries.getRemoteKeyById(id = id).executeAsOneOrNull()
        }
    }

    override fun getAllRemoteKey(): Flow<List<RemoteEntity>> {
        return keyQueries.getAllRemoteKey().asFlow().mapToList()
    }

    override suspend fun deleteRemoteKeyById(id: Long) {
        withContext(io) {
            keyQueries.deleteRemoteKeyById(id = id)
        }
    }

    override suspend fun insertRemoteKey(id: Long?, prevPage: String?, nextPage: String?) {
        withContext(io) {
            keyQueries.insertRemoteKey(
                id = id,
                prevPage = prevPage,
                nextPage = nextPage,
            )
        }
    }


}