package com.aditya.test_util

import com.aditya.logger.disableLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

@OptIn(ExperimentalCoroutinesApi::class)
class TestExtension : BeforeEachCallback, AfterEachCallback {
    private val testDispatcher = StandardTestDispatcher()

    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(testDispatcher)
        disableLogging()
    }

    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }
}