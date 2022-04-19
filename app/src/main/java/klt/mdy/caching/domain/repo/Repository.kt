package klt.mdy.caching.domain.repo

import androidx.paging.PagingData
import klt.mdy.caching.app.common.Resource
import klt.mdy.caching.domain.model.MovieVo
import klt.mdy.caching.domain.model.MovieWithIndexVo
import kotlinx.coroutines.flow.Flow
import paging.moviedb.MovieEntity
import paging.moviedb.RemoteEntity

interface Repository {
    suspend fun getMovies(): Flow<Resource<List<MovieVo>>>
    suspend fun getMoviesFromNetwork(): Flow<PagingData<MovieWithIndexVo>>
    suspend fun getMoviesFromDb(): Flow<PagingData<MovieWithIndexVo>>

    suspend fun getMovieById(id: Long): MovieEntity?
    fun getAllMovies(): Flow<List<MovieEntity>>
    suspend fun deleteMovieById(id: Long)
    suspend fun insertMovie(
        id: Long? = null,
        imageUrl: String,
        posterUrl: String,
        overview: String,
        originalTitle: String,
        title: String,
        releasedDate: String,
        popularity: Double,
        voteCount: Double,
        voteAverage: Double,
    )

    suspend fun getRemoteKeyById(id: Long): RemoteEntity?
    fun getAllRemoteKey(): Flow<List<RemoteEntity>>
    suspend fun deleteRemoteKeyById(id: Long)
    suspend fun insertRemoteKey(
        id: Long? = null,
        prevPage: String?,
        nextPage: String?,
    )
}