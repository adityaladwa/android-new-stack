package com.aditya.analytics.store

import com.aditya.analytics.AnalyticEvent

interface AnalyticsStore {
    val size: Int
    suspend fun store(event: AnalyticEvent)
    suspend fun batch(batchSize: Int): List<AnalyticEvent>
}