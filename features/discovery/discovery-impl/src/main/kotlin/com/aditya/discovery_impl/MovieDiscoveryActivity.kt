package com.aditya.discovery_impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aditya.data.ViewModelResult
import com.aditya.discovery_api.DiscoverMovieResponse
import com.aditya.ui.theme.AndroidnewstackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDiscoveryActivity : ComponentActivity() {
    private val viewModel: MovieDiscoveryViewModel by viewModels()

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, MovieDiscoveryActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidnewstackTheme {
                Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                    MovieDiscoveryScreen(Modifier.padding(innerPadding), viewModel)
                }
            }
        }
    }
}

@Composable
fun MovieDiscoveryScreen(modifier: Modifier, viewModel: MovieDiscoveryViewModel) {
    val moviesFlow = remember { viewModel.discoverMovies() }
    val result by moviesFlow.collectAsStateWithLifecycle()
    when (val currentResult = result) {
        is ViewModelResult.Error -> {
            ShowError(modifier, currentResult.exception)
        }

        ViewModelResult.Loading -> ProgressLoader()
        is ViewModelResult.Success -> {
            ShowMovies(modifier, currentResult.data.movies)
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
fun ShowMovies(modifier: Modifier, movies: List<DiscoverMovieResponse.Movie>) {
    LazyColumn(modifier = modifier) {
        items(movies) {
            MovieItem(it)
        }
    }
}

@Composable
private fun MovieItem(movie: DiscoverMovieResponse.Movie) {
    Row {
        Text(
            modifier = Modifier
                .clickable { }
                .fillMaxWidth()
                .padding(16.dp),
            text = movie.title
        )
    }
}

@Composable
fun ProgressLoader() {
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}
