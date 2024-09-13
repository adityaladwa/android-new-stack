package com.aditya.analytics

import android.content.Context
import com.aditya.analytics.dispatcher.LocalAnalyticsDispatcher
import com.aditya.analytics.store.DiskQueueFileAnalyticsStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

internal const val ANALYTICS_FILE_NAME = "analytics.data"

@Module
@InstallIn(SingletonComponent::class)
class AnalyticsModule {

    @Provides
    @Singleton
    fun providesAnalytics(file: File): Analytics {
        return AnalyticsImpl(
            LocalAnalyticsDispatcher(DispatcherKey.LOCAL_DISPATCHER),
            DiskQueueFileAnalyticsStore(file)
        )
    }

    @Provides
    @Singleton
    fun providesFile(@ApplicationContext context: Context): File {
        return File(context.filesDir, ANALYTICS_FILE_NAME)
    }
}