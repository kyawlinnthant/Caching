package klt.mdy.caching.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RetryView(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Surface {
        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.error
            )
            Spacer(modifier = modifier.height(8.dp))
            OutlinedButton(onClick = onClickRetry) {
                Text(text = "Try again")
            }
        }
    }

}

@Composable
@Preview
private fun Preview() {
    Surface {
        RetryView(message = "Can't connect server",
            onClickRetry = {})
    }
}