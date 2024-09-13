package com.aditya.test_util

import com.aditya.logger.disableLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
class TestExtension : BeforeEachCallback, AfterEachCallback, AfterAllCallback {
    private val testDispatcher = StandardTestDispatcher()

    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(testDispatcher)
        disableLogging()
    }

    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }

    override fun afterAll(context: ExtensionContext?) {
        val testFile = File(TestAnalyticsModule.TEST_ANALYTICS_FILE)
        if (testFile.exists()) {
            testFile.delete()
        }
    }
}