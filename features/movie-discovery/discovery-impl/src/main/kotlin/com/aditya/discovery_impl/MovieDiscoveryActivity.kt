package com.aditya.discovery_impl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.aditya.data.BuildConfig
import com.aditya.data.ViewModelResult
import com.aditya.discovery_api.DiscoverMovieResponse
import com.aditya.movie_detail_api.MovieDetailNavigator
import com.aditya.ui.theme.AndroidnewstackTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDiscoveryActivity : ComponentActivity() {
    private val viewModel: MovieDiscoveryViewModel by viewModels()

    @Inject
    lateinit var movieDetailNavigator: MovieDetailNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidnewstackTheme {
                Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                    MovieDiscoveryScreen(Modifier.padding(innerPadding), viewModel, movieDetailNavigator)
                }
            }
        }
    }
}

@Composable
fun MovieDiscoveryScreen(
    modifier: Modifier,
    viewModel: MovieDiscoveryViewModel,
    movieDetailNavigator: MovieDetailNavigator
) {
    val moviesFlow = remember { viewModel.discoverMovies() }
    val result by moviesFlow.collectAsStateWithLifecycle()
    when (val currentResult = result) {
        is ViewModelResult.Error -> {
            ShowError(modifier, currentResult.exception)
        }

        ViewModelResult.Loading -> ProgressLoader()
        is ViewModelResult.Success -> {
            ShowMoviesGrid(modifier, currentResult.data.movies, movieDetailNavigator)
        }
    }
}

@Composable
fun ShowError(modifier: Modifier, exception: Throwable) {
    Text(
        modifier = modifier,
        text = exception.message ?: "Something went wrong"
    )
}

@Composable
fun ShowMoviesGrid(
    modifier: Modifier,
    movies: List<DiscoverMovieResponse.Movie>,
    movieDetailNavigator: MovieDetailNavigator
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(128.dp),
    ) {
        items(movies) {
            Box(Modifier.clickable {
                movieDetailNavigator.navigateToMovieDetail(it.id)
            }) {
                AsyncImage(
                    model = it.posterUrl(),
                    contentDescription = it.title,
                )
            }
        }
    }
}


fun DiscoverMovieResponse.Movie.posterUrl(): String {
    return BuildConfig.TMDB_IMAGE_BASE_URL.plus(posterPath)
}

@Composable
fun ProgressLoader() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}
