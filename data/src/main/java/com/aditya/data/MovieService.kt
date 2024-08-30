package com.aditya.data

import com.aditya.discovery_api.DiscoverMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    //    'https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc'
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("page") page: Int = 1,
    ): DiscoverMovieResponse
}