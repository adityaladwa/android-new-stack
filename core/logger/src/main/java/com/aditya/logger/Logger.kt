package com.aditya.logger

import android.util.Log
import kotlin.LazyThreadSafetyMode.NONE

val logger by lazy(NONE) { RealLogger() }

interface Logger {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String, error: Throwable? = null)
    fun i(tag: String, message: String)
}

enum class LogLevel {
    DEBUG,
    ERROR,
    INFO
}

fun interface LogHandler {
    fun log(
        level: LogLevel,
        tag: String,
        message: String,
        throwable: Throwable?
    )
}

fun interface LogFormatter {
    fun format(level: LogLevel, tag: String, message: String): String
}

val consoleLogFormatter = LogFormatter { level, tag, message ->
    "[$level] - $tag : $message"
}

val consoleLogHandler = LogHandler { level, tag, message, throwable ->
    val formattedMessage = consoleLogFormatter.format(level, tag, message)
    when (level) {
        LogLevel.DEBUG -> Log.d(tag, formattedMessage)
        LogLevel.ERROR -> Log.e(tag, formattedMessage, throwable)
        LogLevel.INFO -> Log.i(tag, formattedMessage)
    }
}

class RealLogger(
    private val logHandlers: List<LogHandler> = listOf(consoleLogHandler)
) : Logger {
    override fun d(tag: String, message: String) = log(LogLevel.DEBUG, tag, message)

    override fun e(tag: String, message: String, error: Throwable?) =
        log(LogLevel.ERROR, tag, message, error)

    override fun i(tag: String, message: String) = log(LogLevel.INFO, tag, message)

    private fun log(level: LogLevel, tag: String, message: String, throwable: Throwable? = null) {
        for (handler in logHandlers) {
            handler.log(level, tag, message, throwable)
        }
    }
}