package com.aditya.discovery_impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.analytics.Analytics
import com.aditya.analytics.EventName.Companion.event
import com.aditya.data.MovieService
import com.aditya.data.ViewModelResult
import com.aditya.discovery_api.DiscoverMovieResponse
import com.aditya.logger.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieDiscoveryViewModel @Inject constructor(
    private val movieService: MovieService,
    private val analytics: Analytics
) : ViewModel() {

    fun discoverMovies(): StateFlow<ViewModelResult<DiscoverMovieResponse>> {
        logger.d("Discovering movies")
        analytics.track("discover_movie".event)
        return flow<ViewModelResult<DiscoverMovieResponse>> {
            val response = movieService.discoverMovies()
            emit(ViewModelResult.Success(response))
        }.catch {
            emit(ViewModelResult.Error(it))
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            ViewModelResult.Loading
        )
    }
}