package com.aditya.discovery_impl

import app.cash.turbine.test
import com.aditya.analytics.EventName.Companion.event
import com.aditya.analytics.fake.FakeAnalytics
import com.aditya.data.MovieService
import com.aditya.test_util.TestExtension
import com.aditya.test_util.TestNetworkModule
import com.aditya.test_util.getJsonFromResource
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestExtension::class)
class MovieDiscoveryViewModelTest {
    private val server = MockWebServer()
    private val retrofit = TestNetworkModule.retrofit(server.url("/").toString())
    private val analytics = FakeAnalytics.fakeAnalytics()
    private lateinit var viewModel: MovieDiscoveryViewModel

    @BeforeEach
    fun setUp() {
        viewModel = MovieDiscoveryViewModel(retrofit.create(MovieService::class.java), analytics)
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun testViewModelSuccessFlow() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJsonFromResource("movie-discovery-response.json"))

        server.enqueue(mockResponse)
        viewModel.discoverMovies().test {
            assert(awaitItem() is ViewModelResult.Loading)
            val response = awaitItem() as ViewModelResult.Success
            assert(response.data.movies.isNotEmpty())
            assert(response.data.movies.size == 20)
            assert(response.data.page == 1)
        }

        analytics.assertEvent("discover_movie".event)
    }

    @Test
    fun testViewModelErrorFlow() = runTest {
        val error = MockResponse()
            .setResponseCode(400)
            .setBody("Bad Request")

        server.enqueue(error)

        viewModel.discoverMovies().test {
            assert(awaitItem() is ViewModelResult.Loading)
            val response = awaitItem() as ViewModelResult.Error
            assert(response.exception is Exception)
        }
    }
}