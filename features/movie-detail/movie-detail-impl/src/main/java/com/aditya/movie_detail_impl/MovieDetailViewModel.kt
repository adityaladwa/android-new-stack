package com.aditya.movie_detail_impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.data.repository.DataResult
import com.aditya.data.repository.MovieRepository
import com.aditya.logger.logger
import com.aditya.movie_detail_api.MovieDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _movieId = MutableStateFlow<Int?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<UiState> = _movieId
        .filterNotNull()
        .flatMapLatest { movieId ->
            combine(movieDetailsFlow(movieId)) { it[0] }
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            UiState.Loading
        )

    fun setMovieId(movieId: Int) {
        _movieId.update { movieId }
    }

    private fun movieDetailsFlow(movieId: Int): Flow<UiState> {
        logger.d("Fetching movie details for movieId: $movieId")
        return movieRepository
            .movieDetail(movieId)
            .map {
                when (it) {
                    is DataResult.Error -> UiState.Error(it.exception)
                    is DataResult.Success -> UiState.ShowMovieDetails(it.data)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    sealed interface UiState {
        data object Loading : UiState
        data class ShowMovieDetails(val data: MovieDetailResponse) : UiState
        data class Error(val error: Throwable) : UiState
    }
}