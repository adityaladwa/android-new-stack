package com.aditya.test_util

import com.aditya.analytics.Analytics
import com.aditya.analytics.AnalyticsModule
import com.aditya.data.NetworkModule
import retrofit2.Retrofit

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
    private val analyticsModule = AnalyticsModule()

    fun fakeAnalytics(): Analytics {
        return analyticsModule.providesAnalytics()
    }
}