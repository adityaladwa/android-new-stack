package com.aditya.movie_detail_impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.data.MovieService
import com.aditya.data.ViewModelResult
import com.aditya.logger.logger
import com.aditya.movie_detail_api.MovieDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieService: MovieService,
) : ViewModel() {

    fun movieDetailsFlow(movieId: Int): StateFlow<ViewModelResult<MovieDetailResponse>> {
        logger.d("Fetching movie details for movieId: $movieId")
        return flow<ViewModelResult<MovieDetailResponse>> {
            val movieDetail = movieService.movieDetails(movieId)
            emit(ViewModelResult.Success(movieDetail))
        }.catch { e ->
            emit(ViewModelResult.Error(e))
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            ViewModelResult.Loading
        )
    }
}