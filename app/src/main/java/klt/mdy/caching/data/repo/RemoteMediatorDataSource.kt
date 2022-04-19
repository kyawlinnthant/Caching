package klt.mdy.caching.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import klt.mdy.caching.BuildConfig
import klt.mdy.caching.MovieDatabase
import klt.mdy.caching.app.common.Endpoint
import klt.mdy.caching.data.model.ResponseDto
import klt.mdy.caching.domain.model.MovieWithIndexVo
import paging.moviedb.RemoteEntity

@OptIn(ExperimentalPagingApi::class)
class RemoteMediatorDataSource constructor(
    private val client: HttpClient,
    private val movieDb: MovieDatabase,
) : RemoteMediator<Int, MovieWithIndexVo>() {

    private val movieQueries = movieDb.movieEntityQueries
    private val keyQueries = movieDb.remoteEntityQueries

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieWithIndexVo>
    ): MediatorResult {
        val request = Endpoint.BASE_URL
        return try {
            val currentPageForMediator: Int = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    val returnPage = remoteKeys?.nextPage?.let {
                        it.toInt() - 1
                    } ?: 1
                    returnPage
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage.toInt()
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage.toInt()
                }
            }
            val response = client.request<ResponseDto>(request) {
                method = HttpMethod.Get
                parameter(
                    key = Endpoint.PARAMS_API_KEY,
                    value = BuildConfig.API_KEY
                )
                parameter(
                    key = Endpoint.PARAMS_PAGE,
                    value = currentPageForMediator
                )
            }.toVo().mapIndexed { index, movieVo ->
                movieVo.toVoWithIndex(index = index)
            }
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPageForMediator == 1) null else currentPageForMediator - 1
            val nextPage = if (endOfPaginationReached) null else currentPageForMediator + 1

            movieDb.transaction {
                if (loadType == LoadType.REFRESH) {
                    movieQueries.deleteMovieById(1)
                    keyQueries.deleteRemoteKeyById(1)
                }
                val keys = response.map { vo ->
                    RemoteEntity(
                        id = vo.id,
                        prevPage = prevPage.toString(),
                        nextPage = nextPage.toString()
                    )
                }
                response.forEach { indexVo ->

                    movieQueries.insertMovie(
                        id = indexVo.id,
                        imageUrl = indexVo.imageUrl,
                        posterUrl = indexVo.posterUrl,
                        overview = indexVo.overview,
                        originalTitle = indexVo.originalTitle,
                        title = indexVo.title,
                        releasedDate = indexVo.releasedDate,
                        popularity = indexVo.popularity,
                        voteCount = indexVo.voteCount,
                        voteAverage = indexVo.voteAverage
                    )
                }
                keys.forEach { remoteKey ->

                    keyQueries.insertRemoteKey(
                        id = remoteKey.id,
                        prevPage = remoteKey.prevPage,
                        nextPage = remoteKey.nextPage,
                    )
                }
            }
            MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }

    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieWithIndexVo>
    ): RemoteEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                keyQueries.getRemoteKeyById(id = id).executeAsOneOrNull()
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieWithIndexVo>
    ): RemoteEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { vo ->
            keyQueries.getRemoteKeyById(id = vo.id).executeAsOneOrNull()
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieWithIndexVo>
    ): RemoteEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { vo ->
            keyQueries.getRemoteKeyById(id = vo.id).executeAsOneOrNull()
        }
    }

}