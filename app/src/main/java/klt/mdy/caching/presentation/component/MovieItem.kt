package klt.mdy.caching.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import klt.mdy.caching.domain.model.MovieWithIndexVo

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: MovieWithIndexVo
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colors.primary.copy(alpha = 0.1f))
            .padding(all = 12.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            text = "${movie.index}.${movie.originalTitle}",
            style = MaterialTheme.typography.h4
        )
        if (movie.imageUrl.isNotEmpty()) {
            val image = rememberCoilPainter(
                request = movie.imageUrl,
                fadeIn = true
            )
            Image(
                painter = image,
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = modifier.aspectRatio(ratio = 2.0f)
            )
        }
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            text = movie.overview,
            style = MaterialTheme.typography.overline
        )
        Spacer(modifier = modifier.height(8.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "popularity : ${movie.popularity}",
                style = MaterialTheme.typography.overline
            )
            Text(
                text = "vote count : ${movie.voteCount}",
                style = MaterialTheme.typography.overline
            )
            Text(
                text = "vote average : ${movie.voteAverage}",
                style = MaterialTheme.typography.overline
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    Surface {
        MovieItem(
            movie = MovieWithIndexVo(
                index = 1,
                id = 1,
                imageUrl = "",
                posterUrl = "",
                overview = "This is the overview of the selected movie item. " +
                        "How's about the content preview. This gonna be GG. " +
                        "This overview will contain the description of a movie.",
                originalTitle = "Original Title",
                title = "Title",
                popularity = 1.2,
                releasedDate = "12.12.2022",
                voteCount = 1.2,
                voteAverage = 1.2
            )
        )
    }
}