package com.aditya.analytics

import com.aditya.analytics.dispatcher.AnalyticsDispatcher
import com.aditya.analytics.store.AnalyticsStore
import com.aditya.logger.logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Serializable
data class AnalyticEvent(
    val eventName: String,
    val id: String = UUID.randomUUID().toString(),
    val eventProperties: Map<String, String?> = mutableMapOf(),
    val superProperties: Map<String, String?> = mutableMapOf()
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