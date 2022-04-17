package klt.mdy.caching.domain.model

data class MovieWithIndexVo(
    val index: Int = -1,
    val id: Long = -1,
    val imageUrl: String = "",
    val posterUrl: String = "",
    val overview: String = "",
    val originalTitle: String = "",
    val title: String = "",
    val popularity: Double = 0.0,
    val releasedDate: String = "",
    val voteCount: Double = 0.0,
    val voteAverage: Double = 0.0,
)
