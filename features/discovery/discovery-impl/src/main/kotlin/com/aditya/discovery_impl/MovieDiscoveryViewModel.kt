package com.aditya.discovery_impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.data.MovieService
import com.aditya.discovery_api.DiscoverMovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieDiscoveryViewModel @Inject constructor(
    private val movieService: MovieService
) : ViewModel() {

    fun discoverMovies(): StateFlow<ViewModelResult<DiscoverMovieResponse>> {
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

    sealed class ViewModelResult<out T> {
        data object Loading : ViewModelResult<Nothing>()
        data class Success<out T>(val data: T) : ViewModelResult<T>()
        data class Error(val exception: Throwable) : ViewModelResult<Nothing>()
    }
}