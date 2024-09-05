package com.aditya.data

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