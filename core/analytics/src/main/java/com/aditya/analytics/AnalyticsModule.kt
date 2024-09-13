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

@Module
@InstallIn(SingletonComponent::class)
class AnalyticsModule {

    @Provides
    @Singleton
    fun providesAnalytics(@ApplicationContext context: Context): Analytics {
        return AnalyticsImpl(
            LocalAnalyticsDispatcher(DispatcherKey.LOCAL_DISPATCHER),
            DiskQueueFileAnalyticsStore(File(context.filesDir, "analytics.data"))
        )
    }
}