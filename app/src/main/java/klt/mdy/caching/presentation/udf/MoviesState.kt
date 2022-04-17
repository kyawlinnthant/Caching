package klt.mdy.caching.presentation.udf

import androidx.paging.PagingData
import klt.mdy.caching.domain.model.MovieWithIndexVo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MoviesState(
    val movies: Flow<PagingData<MovieWithIndexVo>> = emptyFlow()
)
