package com.aditya.analytics.store

import com.aditya.analytics.AnalyticEvent
import com.aditya.logger.logger
import com.squareup.tape2.QueueFile
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class DiskQueueFileAnalyticsStore(
    file: File
) : AnalyticsStore {
    private val queueFile = QueueFile.Builder(file).build()
    private val mutex = Mutex()

    override val size: Int
        get() = queueFile.size()

    override suspend fun store(event: AnalyticEvent) {
        mutex.withLock {
            val bytes = serialize(event)
            queueFile.add(bytes)
        }
    }

    override suspend fun batch(batchSize: Int): List<AnalyticEvent> {
        return mutex.withLock {
            val events = mutableListOf<AnalyticEvent>()
            for (i in 0..<batchSize) {
                val bytes = queueFile.peek() ?: break
                events.add(deserialize(bytes))
                queueFile.remove()
            }
            events
        }
    }

    private fun serialize(event: AnalyticEvent): ByteArray {
        val jsonString = Json.encodeToString(event)
        return jsonString.toByteArray()
    }

    private fun deserialize(bytes: ByteArray): AnalyticEvent {
        val jsonString = String(bytes)
        return try {
            Json.decodeFromString(jsonString)
        } catch (e: SerializationException) {
            logger.e("Error deserializing event: $e")
            throw e
        }
    }
}