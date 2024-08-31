package com.aditya.movie_detail_impl

import com.aditya.movie_detail_api.Navigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class MovieDetailModule {

    @Binds
    abstract fun provideNavigation(
        realNavigator: RealNavigator
    ): Navigator
}