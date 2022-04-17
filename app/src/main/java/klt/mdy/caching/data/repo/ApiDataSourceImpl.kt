package klt.mdy.caching.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import klt.mdy.caching.BuildConfig
import klt.mdy.caching.app.common.Constant
import klt.mdy.caching.app.common.Endpoint
import klt.mdy.caching.app.common.Resource
import klt.mdy.caching.app.common.safeApiCall
import klt.mdy.caching.app.di.Qualifier
import klt.mdy.caching.data.model.ResponseDto
import klt.mdy.caching.domain.model.MovieVo
import klt.mdy.caching.domain.model.MovieWithIndexVo
import klt.mdy.caching.domain.repo.ApiDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class ApiDataSourceImpl @Inject constructor(
    private val client: HttpClient,
    @Qualifier.Io private val io: CoroutineDispatcher,
) : ApiDataSource {
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

    override suspend fun getPaginatedMovies(): Flow<PagingData<MovieWithIndexVo>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                PagingDataSource(
                    client = client
                )
            }
        ).flow
    }
}