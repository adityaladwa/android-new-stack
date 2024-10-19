package com.aditya.discovery_impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.analytics.Analytics
import com.aditya.analytics.EventName.Companion.event
import com.aditya.data.repository.DataResult
import com.aditya.data.repository.MovieRepository
import com.aditya.discovery_api.DiscoverMovieResponse
import com.aditya.logger.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieDiscoveryViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val analytics: Analytics
) : ViewModel() {

    val uiState = discoverMoviesUiState()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            UiState.Loading
        )

    private fun discoverMoviesUiState(): Flow<UiState> {
        return combine(discoverMovies()) { it[0] }
    }

    private fun discoverMovies(): Flow<UiState> {
        logger.d("Discovering movies")
        analytics.track("discover_movie".event)
        return movieRepository
            .discoverMovies()
            .map { result ->
                when (result) {
                    is DataResult.Error -> UiState.Error(result.exception)
                    is DataResult.Success -> UiState.Success(result.data)
                }
            }
            .flowOn(Dispatchers.IO)
    }


    sealed interface UiState {
        data object Loading : UiState
        data class Success(val data: DiscoverMovieResponse) : UiState
        data class Error(val error: Throwable) : UiState
    }
}