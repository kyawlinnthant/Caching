package klt.mdy.caching.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerItem(
    modifier: Modifier = Modifier,
    brush: Brush
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colors.primary.copy(alpha = 0.1f))
            .padding(all = 12.dp),
    ) {
        Spacer(
            modifier = modifier
                .fillMaxWidth(fraction = 0.6f)
                .height(height = 50.dp)
                .padding(all = 8.dp)
                .background(brush = brush),
        )

        Spacer(
            modifier = modifier
                .aspectRatio(ratio = 2.0f)
                .padding(all = 8.dp)
                .background(brush = brush)
        )
        Spacer(modifier = modifier.height(8.dp))

        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(height = 24.dp)
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp,
                )
                .background(brush = brush),
        )
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(height = 24.dp)
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp,
                )
                .background(brush = brush),
        )
        Spacer(
            modifier = modifier
                .fillMaxWidth(fraction = 0.4f)
                .height(height = 24.dp)
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp,
                )
                .background(brush = brush),
        )
        Spacer(modifier = modifier.height(8.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(
                modifier = modifier
                    .width(width = 56.dp)
                    .height(height = 24.dp)
                    .background(brush = brush)
                    .padding(all = 8.dp),
            )
            Spacer(
                modifier = modifier
                    .width(width = 56.dp)
                    .height(height = 24.dp)
                    .background(brush = brush)
                    .padding(all = 8.dp),
            )
            Spacer(
                modifier = modifier
                    .width(width = 56.dp)
                    .height(height = 24.dp)
                    .background(brush = brush)
                    .padding(all = 8.dp),
            )
        }

    }
}

@Composable
@Preview
private fun Preview() {

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.5f),
        Color.LightGray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.5f),
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
    )
    Surface {
        ShimmerItem(brush = brush)
    }
}