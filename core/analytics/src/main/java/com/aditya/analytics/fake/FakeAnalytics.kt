package com.aditya.analytics.fake

import com.aditya.analytics.AnalyticEvent
import com.aditya.analytics.Analytics
import com.aditya.analytics.AnalyticsImpl
import com.aditya.analytics.DispatcherKey
import com.aditya.analytics.EventName
import com.aditya.analytics.dispatcher.AnalyticsDispatcher
import com.aditya.analytics.dispatcher.LocalAnalyticsDispatcher
import com.aditya.analytics.store.InMemoryAnalyticsStore
import kotlin.time.Duration.Companion.seconds

class FakeAnalytics(
    private val dispatcher: AnalyticsDispatcher,
    private val store: InMemoryAnalyticsStore,
) : Analytics by AnalyticsImpl(dispatcher, store, 10.seconds, 10) {

    companion object {
        fun fakeAnalytics(): FakeAnalytics {
            return FakeAnalytics(
                LocalAnalyticsDispatcher(DispatcherKey.LOCAL_DISPATCHER),
                InMemoryAnalyticsStore()
            )
        }
    }

    fun assertEvent(event: AnalyticEvent) {
        assert(store.exist(event)) { "Event not found: $event" }
    }

    fun assertEvent(event: EventName, times: Int = 1) {
        assert(store.exist(event, times)) { "Event not found: $event" }
    }
}