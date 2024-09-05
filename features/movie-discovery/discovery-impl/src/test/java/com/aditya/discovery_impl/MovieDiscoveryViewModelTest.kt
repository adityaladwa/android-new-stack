package com.aditya.discovery_impl

import com.aditya.data.MovieService
import com.aditya.data.TestNetworkModule
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MovieDiscoveryViewModelTest {
    private val server = MockWebServer()
    private val retrofit = TestNetworkModule.retrofit(server.url("/").toString())
    private lateinit var viewModel: MovieDiscoveryViewModel

    @BeforeEach
    fun setUp() {
        viewModel = MovieDiscoveryViewModel(retrofit.create(MovieService::class.java))
    }

    @AfterEach
    fun tearDown() {

    }

    @Test
    fun testViewModel() {
        viewModel.discoverMovies()
    }
}