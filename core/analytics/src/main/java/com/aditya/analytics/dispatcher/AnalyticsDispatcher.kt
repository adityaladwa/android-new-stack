package com.aditya.analytics.dispatcher

import com.aditya.analytics.AnalyticEvent
import com.aditya.analytics.DispatcherKey

interface AnalyticsDispatcher {
    val key: DispatcherKey
    suspend fun dispatch(event: AnalyticEvent)
    suspend fun dispatch(events: List<AnalyticEvent>)
}