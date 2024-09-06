package com.aditya.movie_detail_impl

import com.aditya.movie_detail_api.MovieDetailNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class MovieDetailModule {

    @Binds
    abstract fun provideNavigation(
        realNavigator: RealMovieDetailNavigator
    ): MovieDetailNavigator
}