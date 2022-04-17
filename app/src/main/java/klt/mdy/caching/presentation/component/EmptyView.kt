package klt.mdy.caching.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EmptyView(
    modifier : Modifier = Modifier,
){
    Surface {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                modifier = modifier.fillMaxWidth(),
                text = "This is Empty Data View",
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
@Preview
private fun Preview() {
    Surface {
        EmptyView()
    }
}