package com.aditya.test_util

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