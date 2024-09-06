package com.aditya.movie_detail_impl

import app.cash.turbine.test
import com.aditya.data.MovieService
import com.aditya.data.ViewModelResult
import com.aditya.test_util.TestNetworkModule
import com.aditya.test_util.getJsonFromResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException


class MovieDetailViewModelTest {
    private val server = MockWebServer()
    private val testDispatcher = StandardTestDispatcher()
    private val retrofit = TestNetworkModule.retrofit(server.url("/").toString())
    private lateinit var viewModel: MovieDetailViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieDetailViewModel(retrofit.create(MovieService::class.java))
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        server.shutdown()
    }

    @Test
    fun testViewModelSuccessFlow() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJsonFromResource("movie-detail-response.json"))

        server.enqueue(mockResponse)

        viewModel.movieDetailsFlow(1).test {
            assert(awaitItem() is ViewModelResult.Loading)

            val response = awaitItem() as ViewModelResult.Success
            assert(response.data.id == 1022789)
            assert(response.data.title == "Inside Out 2")
            assert(response.data.backdropPath.isNotEmpty())
        }
    }

    @Test
    fun testViewModelErrorFlow() = runTest {
        val errorResponse = MockResponse()
            .setResponseCode(404)
            .setBody("Not Found")

        server.enqueue(errorResponse)

        viewModel.movieDetailsFlow(1).test {
            assert(awaitItem() is ViewModelResult.Loading)
            val error = awaitItem() as ViewModelResult.Error
            assert(error.exception is HttpException)
        }
    }
}
