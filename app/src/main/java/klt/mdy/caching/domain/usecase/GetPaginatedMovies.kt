package klt.mdy.caching.domain.usecase

import androidx.paging.PagingData
import klt.mdy.caching.data.repo.ApiDataSourceImpl
import klt.mdy.caching.domain.model.MovieWithIndexVo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPaginatedMovies @Inject constructor(
    private val repo: ApiDataSourceImpl
) {
    suspend operator fun invoke(): Flow<PagingData<MovieWithIndexVo>> {
        return repo.getPaginatedMovies()
    }
}