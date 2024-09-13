package com.aditya.test_util

import com.aditya.analytics.Analytics
import com.aditya.analytics.AnalyticsModule
import com.aditya.data.NetworkModule
import retrofit2.Retrofit
import java.io.File

object TestNetworkModule {
    private val networkModule = NetworkModule()

    fun retrofit(baseUrl: String): Retrofit {
        return networkModule.retrofit(
            networkModule.provideOkhttpClient(networkModule.apiKeyInterceptor()),
            networkModule.jsonConfig(),
            baseUrl
        )
    }
}

object TestAnalyticsModule {
    const val TEST_ANALYTICS_FILE = "analytics.data"
    private val analyticsModule = AnalyticsModule()

    // TODO: Implement fake analytics with assert methods
    fun fakeAnalytics(): Analytics {
        return analyticsModule.providesAnalytics(
            File(TEST_ANALYTICS_FILE)
        )
    }
}