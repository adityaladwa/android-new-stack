package com.aditya.data.repository

import com.aditya.data.MovieService
import com.aditya.discovery_api.DiscoverMovieResponse
import com.aditya.movie_detail_api.MovieDetailResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieService: MovieService
) {

    fun discoverMovies(): Flow<DataResult<DiscoverMovieResponse>> {
        return flow<DataResult<DiscoverMovieResponse>> {
            val response = movieService.discoverMovies()
            emit(DataResult.Success(response))
        }.catch {
            emit(DataResult.Error(it))
        }
    }

    fun movieDetail(movieId: Int): Flow<DataResult<MovieDetailResponse>> {
        return flow<DataResult<MovieDetailResponse>> {
            val movieDetail = movieService.movieDetails(movieId)
            emit(DataResult.Success(movieDetail))
        }.catch { e ->
            emit(DataResult.Error(e))
        }
    }
}