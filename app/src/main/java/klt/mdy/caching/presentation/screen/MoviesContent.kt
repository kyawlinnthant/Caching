package klt.mdy.caching.presentation.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import klt.mdy.caching.domain.model.MovieWithIndexVo
import klt.mdy.caching.presentation.component.*

@Composable
fun MoviesContent(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<MovieWithIndexVo>,
) {
    Surface {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(all = 8.dp),
            content = {
                itemsIndexed(items = movies) { index, value ->
                    MovieItem(movie = value ?: MovieWithIndexVo())
                    if (index < movies.itemCount - 1) {

                        Spacer(modifier = modifier.height(8.dp))

                    }
                }
                movies.apply {

                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                ShimmerView()
                            }
                        }
                        loadState.append is LoadState.Loading -> {
                            item {
                                LoadingView()
                            }
                        }
                        loadState.refresh is LoadState.Error -> {
                            val e = movies.loadState.refresh as LoadState.Error
                            item {
                                RetryView(
                                    message = e.error.localizedMessage ?: "Error",
                                    modifier = modifier.fillParentMaxSize(),
                                    onClickRetry = { retry() }
                                )
                            }
                        }
                        loadState.append is LoadState.Error -> {
                            val e = movies.loadState.append as LoadState.Error
                            item {
                                RetryView(
                                    message = e.error.localizedMessage ?: "Error",
                                    modifier = modifier.fillParentMaxSize(),
                                    onClickRetry = { retry() }
                                )
                            }
                        }
                        loadState.append.endOfPaginationReached -> {
                            if (movies.itemCount == 0) {
                                item {
                                    EmptyView()
                                }
                            } else {
                                item {
                                    EndOfPaginationView()
                                }
                            }
                        }
                    }

                }
            }
        )
    }
}