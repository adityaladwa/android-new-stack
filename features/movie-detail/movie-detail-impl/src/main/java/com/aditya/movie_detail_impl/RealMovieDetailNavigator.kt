package com.aditya.movie_detail_impl

import android.content.Context
import com.aditya.movie_detail_api.MovieDetailNavigator
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class RealMovieDetailNavigator @Inject constructor(
    @ActivityContext private val context: Context
) : MovieDetailNavigator {
    override fun navigateToMovieDetail(movieId: Int) {
        val intent = MovieDetailActivity.intent(context, movieId)
        context.startActivity(intent)
    }
}