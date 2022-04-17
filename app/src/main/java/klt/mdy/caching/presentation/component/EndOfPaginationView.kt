package klt.mdy.caching.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EndOfPaginationView(
    modifier: Modifier = Modifier
) {
    Surface {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            text = "End of pagination",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
private fun Preview() {
    Surface {
        EndOfPaginationView()
    }
}