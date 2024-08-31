package com.aditya.movie_detail_impl

import android.content.Context
import com.aditya.movie_detail_api.Navigator
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RealNavigator @Inject constructor(
    @ActivityContext private val context: Context
) : Navigator {
    override fun navigateToMovieDetail(movieId: Int) {
        val intent = MovieDetailActivity.intent(context, movieId)
        context.startActivity(intent)
    }
}