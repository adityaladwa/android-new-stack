package com.aditya.analytics

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AnalyticsModule {

    @Provides
    @Singleton
    fun providesAnalytics(): Analytics {
        return AnalyticsImpl(
            LocalAnalyticsDispatcher(DispatcherKey.LOCAL_DISPATCHER),
            InMemoryAnalyticsStore()
        )
    }
}