package com.aditya.discovery_impl

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.aditya.data.BuildConfig
import com.aditya.discovery_api.DiscoverMovieResponse
import com.aditya.movie_detail_api.MovieDetailNavigator
import com.aditya.ui.theme.AndroidnewstackTheme

@Composable
fun MovieDiscoveryScreen(
    movieDetailNavigator: MovieDetailNavigator,
    viewModel: MovieDiscoveryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MovieDiscoveryUi(uiState, movieDetailNavigator)
}

@Composable
private fun MovieDiscoveryUi(
    uiState: MovieDiscoveryViewModel.UiState,
    movieDetailNavigator: MovieDetailNavigator
) {
    AndroidnewstackTheme {
        Scaffold(Modifier.fillMaxSize()) { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            when (uiState) {
                is MovieDiscoveryViewModel.UiState.Error -> {
                    ShowError(modifier, uiState.error)
                }

                MovieDiscoveryViewModel.UiState.Loading -> ProgressLoader()
                is MovieDiscoveryViewModel.UiState.Success -> {
                    ShowMoviesGrid(modifier, uiState.data.movies, movieDetailNavigator)
                }
            }
        }
    }
}

@Composable
private fun ShowError(modifier: Modifier, exception: Throwable) {
    Text(
        modifier = modifier,
        text = exception.message ?: "Something went wrong"
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ShowMoviesGrid(
    modifier: Modifier,
    movies: List<DiscoverMovieResponse.Movie>,
    movieDetailNavigator: MovieDetailNavigator
) {
    LazyVerticalGrid(
        modifier = modifier
            .semantics { testTagsAsResourceId = true }
            .testTag("movies_grid"),
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


private fun DiscoverMovieResponse.Movie.posterUrl(): String {
    return BuildConfig.TMDB_IMAGE_BASE_URL.plus(posterPath)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ProgressLoader() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .semantics { testTagsAsResourceId = true }
            .testTag("progress_loader")
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}
