package klt.mdy.caching.app.common

import io.ktor.client.features.*

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Loading<T> : Resource<T>()
    class Error<T>(error: String) : Resource<T>(null, error)
    class Success<T>(data: T) : Resource<T>(data)

}

fun <T> safeApiCall(
    response: T,
): Resource<T> {
    return try {
        Resource.Success(data = response)
    } catch (e: RedirectResponseException) {
        // 3xx
        Resource.Error(error = e.response.status.description)
    } catch (e: ClientRequestException) {
        // 4xx
        Resource.Error(error = e.response.status.description)
    } catch (e: ServerResponseException) {
        // 5xx
        Resource.Error(error = e.response.status.description)
    } catch (e: Exception) {
        Resource.Error(error = e.message.toString())
    }
}