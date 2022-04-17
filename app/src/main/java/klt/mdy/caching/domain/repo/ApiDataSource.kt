package klt.mdy.caching.domain.repo

import androidx.paging.PagingData
import klt.mdy.caching.app.common.Resource
import klt.mdy.caching.domain.model.MovieVo
import klt.mdy.caching.domain.model.MovieWithIndexVo
import kotlinx.coroutines.flow.Flow

interface ApiDataSource {
    suspend fun getMovies(): Flow<Resource<List<MovieVo>>>
    suspend fun getPaginatedMovies(): Flow<PagingData<MovieWithIndexVo>>
}