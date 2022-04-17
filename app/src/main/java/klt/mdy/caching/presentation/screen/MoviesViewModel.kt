package klt.mdy.caching.presentation.screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import klt.mdy.caching.domain.usecase.GetPaginatedMovies
import klt.mdy.caching.presentation.udf.MoviesState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val useCase: GetPaginatedMovies
) : ViewModel() {

    private val _moviesState = mutableStateOf(MoviesState())
    val moviesState: State<MoviesState> get() = _moviesState

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            useCase().collect {
                _moviesState.value = moviesState.value.copy(
                    movies = flow {
                        emit(it)
                    }.cachedIn(this)
                )
            }
        }
    }
}