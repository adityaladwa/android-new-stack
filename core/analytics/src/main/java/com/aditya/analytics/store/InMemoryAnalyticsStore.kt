package com.aditya.analytics.store

import com.aditya.analytics.AnalyticEvent
import com.aditya.analytics.EventName
import com.aditya.logger.logger
import java.util.concurrent.ConcurrentLinkedQueue

class InMemoryAnalyticsStore : AnalyticsStore {
    private val events = ConcurrentLinkedQueue<AnalyticEvent>()

    override val size: Int
        get() = events.size

    override suspend fun store(event: AnalyticEvent) {
        events.offer(event)
        logger.d("event stored: $event")
    }

    override suspend fun batch(batchSize: Int): List<AnalyticEvent> {
        val batch = mutableListOf<AnalyticEvent>()
        for (i in 0..<batchSize) {
            val event = events.poll() ?: break
            batch.add(event)
        }
        return batch.toList()
    }

    fun exist(event: AnalyticEvent): Boolean {
        return event in events
    }

    fun exist(eventName: EventName, count: Int): Boolean {
        return events.count { it.eventName == eventName.name } == count
    }
}