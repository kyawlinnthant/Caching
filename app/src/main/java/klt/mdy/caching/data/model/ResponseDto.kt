package klt.mdy.caching.data.model

import klt.mdy.caching.app.common.Endpoint
import klt.mdy.caching.domain.model.MovieVo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto(
    val dates: Dates,
    val page: Int,
    val results: List<MovieData>,
    val total_pages: Int,
    val total_results: Int
) {
    fun toVo(): List<MovieVo> {
        return results.map {
            it.toVo()
        }
    }
}

@Serializable
data class Dates(
    val maximum: String,
    val minimum: String
)

@Serializable
data class MovieData(
    val id: Long,
    @SerialName(value = "backdrop_path")
    val imageUrl: String?,
    @SerialName(value = "poster_path")
    val posterUrl: String?,
    val overview: String,
    @SerialName(value = "original_title")
    val originalTitle: String,
    val title: String,
    val popularity: Double,
    @SerialName(value = "release_date")
    val releasedDate: String,
    @SerialName(value = "vote_count")
    val voteCount: Double,
    @SerialName(value = "vote_average")
    val voteAverage: Double,

    val genre_ids: List<Int>,
    val original_language: String,
    val video: Boolean,
    val adult: Boolean,
) {
    fun toVo(): MovieVo {
        return MovieVo(
            id = id,
            imageUrl = imageUrl?.let { "${Endpoint.IMAGE_URL}/$it" } ?: "",
            posterUrl = posterUrl?.let { "${Endpoint.IMAGE_URL}/$it" } ?: "",
            overview = overview,
            originalTitle = originalTitle,
            title = title,
            popularity = popularity,
            releasedDate = releasedDate,
            voteCount = voteCount,
            voteAverage = voteAverage,
        )
    }
}

