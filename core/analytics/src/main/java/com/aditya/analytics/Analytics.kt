package com.aditya.analytics

import com.aditya.logger.logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.UUID
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class AnalyticEvent(
    val eventName: String,
    val id: UUID = UUID.randomUUID(),
    val eventProperties: Map<String, Any?> = mutableMapOf(),
    val superProperties: Map<String, Any?> = mutableMapOf()
)

@JvmInline
value class EventName(val name: String) {
    companion object {
        inline val String.event get() = EventName(this)
    }
}

@JvmInline
value class DispatcherKey(val key: String) {
    companion object {
        val LOCAL_DISPATCHER = DispatcherKey("LOCAL_DISPATCHER")
    }
}


interface Analytics {
    fun track(event: AnalyticEvent)
    fun track(eventName: EventName)
}

interface AnalyticsDispatcher {
    val key: DispatcherKey
    suspend fun dispatch(event: AnalyticEvent)
    suspend fun dispatch(events: List<AnalyticEvent>)
}

interface AnalyticsStore {
    val size: Int
    suspend fun store(event: AnalyticEvent)
    fun batch(batchSize: Int): List<AnalyticEvent>
}

class AnalyticsImpl(
    private val dispatcher: AnalyticsDispatcher,
    private val store: AnalyticsStore,
    private val flushInterval: Duration = 10.seconds,
    private val batchSize: Int = 10
) : Analytics {
    private val scope = CoroutineScope(
        Dispatchers.IO.limitedParallelism(2) + SupervisorJob()
    ) // use limited parallelism to reduce lock contention

    init {
        scope.launch {
            while (isActive) {
                delay(flushInterval)
                logger.d("flushing events after $flushInterval")
                flush()
            }
        }
    }

    override fun track(event: AnalyticEvent) {
        scope.launch {
            logger.d("tracking event: $event")
            store.store(event)
            if (store.size >= batchSize) {
                flush()
            }
        }
    }

    override fun track(eventName: EventName) {
        scope.launch {
            logger.d("tracking event: $eventName")
            store.store(AnalyticEvent(eventName.name))
        }
    }

    private suspend fun flush() {
        store.batch(batchSize).also {
            dispatcher.dispatch(it)
        }
    }
}

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

class InMemoryAnalyticsStore : AnalyticsStore {
    private val events = LinkedList<AnalyticEvent>()

    override suspend fun store(event: AnalyticEvent) {
        events.offer(event)
        logger.d("event stored: $event")
    }

    override fun batch(batchSize: Int): List<AnalyticEvent> {
        var remaining = batchSize
        events.take(batchSize)
        val batch = mutableListOf<AnalyticEvent>()
        while (events.isNotEmpty() && remaining >= 0) {
            val event = events.poll()
            event ?: break
            batch.add(event)
            remaining--
        }
        return batch.toList()
    }

    override val size: Int
        get() = events.size
}