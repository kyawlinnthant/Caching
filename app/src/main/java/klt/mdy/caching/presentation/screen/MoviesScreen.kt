package klt.mdy.caching.presentation.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MoviesScreen() {
    val vm: MoviesViewModel = hiltViewModel()
    MoviesView(vm = vm)
}