package com.aditya.logger

import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter

var logger = AndroidLogger()
    private set

fun disableLogging() {
    logger.enabled = false
}

private interface Logger {
    var enabled: Boolean
    fun d(message: String)
    fun e(message: String, error: Throwable? = null)
    fun i(message: String)
    fun w(message: String)
}

fun interface LogHandler {
    fun log(
        priority: Int,
        tag: String,
        message: String,
        throwable: Throwable?
    )
}

fun interface LogFormatter {
    fun format(priority: Int, tag: String, message: String, throwable: Throwable?): String
}

val consoleLogFormatter = LogFormatter { _, _, message, throwable ->
    val formattedMessage = StringBuilder(message)
    if (throwable != null) {
        formattedMessage.append("\n")
        formattedMessage.append(getStackTraceString(throwable))
    }
    formattedMessage.toString()
}

private fun getStackTraceString(t: Throwable): String {
    val sw = StringWriter(256)
    val pw = PrintWriter(sw, false)
    t.printStackTrace(pw)
    pw.flush()
    return sw.toString()
}

val consoleLogHandler = LogHandler { priority, tag, message, throwable ->
    val formattedMessage = consoleLogFormatter.format(priority, tag, message, throwable)
    Log.println(priority, tag, formattedMessage)
}

class AndroidLogger(
    private val logHandlers: List<LogHandler> = listOf(consoleLogHandler),
    override var enabled: Boolean = true
) : com.aditya.logger.Logger {
    override fun d(message: String) = log(Log.DEBUG, getTag(), message)

    override fun e(message: String, error: Throwable?) =
        log(Log.ERROR, getTag(), message, error)

    override fun i(message: String) = log(Log.INFO, getTag(), message)

    override fun w(message: String) {
        log(Log.WARN, getTag(), message)
    }

    private fun log(priority: Int, tag: String, message: String, throwable: Throwable? = null) {
        if (!enabled) return // Do not log if disabled
        for (handler in logHandlers) {
            handler.log(priority, tag, message, throwable)
        }
    }

    private fun getTag(): String {
        return Thread.currentThread().stackTrace[4].className.substringAfterLast('.')
    }
}