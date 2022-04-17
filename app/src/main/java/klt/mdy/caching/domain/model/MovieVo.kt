package klt.mdy.caching.domain.model

data class MovieVo(
    val id: Long,
    val imageUrl: String,
    val posterUrl: String,
    val overview: String,
    val originalTitle: String,
    val title: String,
    val popularity: Double,
    val releasedDate: String,
    val voteCount: Double,
    val voteAverage: Double,
) {
    fun toVoWithIndex(index: Int): MovieWithIndexVo {
        return MovieWithIndexVo(
            index = index,
            id = id,
            imageUrl = imageUrl,
            posterUrl = posterUrl,
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
