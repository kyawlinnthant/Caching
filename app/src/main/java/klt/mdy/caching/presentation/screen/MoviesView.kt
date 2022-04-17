package klt.mdy.caching.presentation.screen

import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun MoviesView(
    vm: MoviesViewModel
) {
    val state = vm.moviesState.value
    val movies = state.movies.collectAsLazyPagingItems()
    MoviesContent(movies = movies)
}