package klt.mdy.caching.data.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import klt.mdy.caching.BuildConfig
import klt.mdy.caching.app.common.Constant
import klt.mdy.caching.app.common.Endpoint
import klt.mdy.caching.data.model.ResponseDto
import klt.mdy.caching.domain.model.MovieWithIndexVo

class PagingDataSource constructor(
    private val client: HttpClient
) : PagingSource<Int, MovieWithIndexVo>() {
    override fun getRefreshKey(state: PagingState<Int, MovieWithIndexVo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieWithIndexVo> {
        val currentPage = params.key ?: Constant.INITIAL_PAGE
        val request = Endpoint.BASE_URL
        return try {

            val response = client.request<ResponseDto>(request) {
                method = HttpMethod.Get
                parameter(
                    key = Endpoint.PARAMS_API_KEY,
                    value = BuildConfig.API_KEY
                )
                parameter(
                    key = Endpoint.PARAMS_PAGE,
                    value = currentPage
                )
            }.toVo().mapIndexed { index, movieVo ->
                movieVo.toVoWithIndex(index = index)
            }


            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isNotEmpty()) currentPage + 1 else null
            )

        } catch (e: RedirectResponseException) {
            // 3xx
            LoadResult.Error(e)
        } catch (e: ClientRequestException) {
            // 4xx
            LoadResult.Error(e)
        } catch (e: ServerResponseException) {
            // 5xx
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}