package com.aditya.analytics.dispatcher

import com.aditya.analytics.AnalyticEvent
import com.aditya.analytics.DispatcherKey
import com.aditya.logger.logger
import kotlinx.coroutines.delay

class LocalAnalyticsDispatcher(override val key: DispatcherKey) : AnalyticsDispatcher {

    override suspend fun dispatch(event: AnalyticEvent) {
        logger.d("dispatching event: $event")
        // simulate some network call
        delay(1000)
        logger.d("event dispatched: $event")
    }

    override suspend fun dispatch(events: List<AnalyticEvent>) {
        for (event in events) {
            dispatch(event)
        }
    }
}
