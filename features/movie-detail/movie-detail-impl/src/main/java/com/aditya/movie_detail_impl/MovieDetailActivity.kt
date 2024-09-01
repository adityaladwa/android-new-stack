package com.aditya.movie_detail_impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.aditya.data.BuildConfig
import com.aditya.data.ViewModelResult
import com.aditya.movie_detail_api.MovieDetailResponse
import com.aditya.ui.theme.AndroidnewstackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : ComponentActivity() {
    private val viewModel: MovieDetailViewModel by viewModels()
    private val movieId by lazy { intent.getIntExtra(MOVIE_ID, 0) }

    companion object {
        private const val MOVIE_ID = "movieId"

        fun intent(context: Context, movieId: Int): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "$movieId", Toast.LENGTH_SHORT).show()
        enableEdgeToEdge()
        setContent {
            AndroidnewstackTheme {
                val movieDetailFlow = remember { viewModel.movieDetailsFlow(movieId) }
                val result by movieDetailFlow.collectAsStateWithLifecycle()
                when (val currentResult = result) {
                    is ViewModelResult.Error -> ShowError(currentResult.exception)
                    ViewModelResult.Loading -> ProgressLoader()
                    is ViewModelResult.Success -> CollapsibleToolbarScreen(currentResult.data)
                }
            }
        }
    }
}

@Composable
fun ShowError(exception: Throwable) {
    Log.e("MovieDetailActivity", "ShowError: ", exception)
    Text(
        text = exception.message ?: "Something went wrong"
    )
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

@Composable
fun CollapsibleToolbarScreen(data: MovieDetailResponse) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = BuildConfig.TMDB_IMAGE_BASE_URL.plus(data.backdropPath),
                contentDescription = "Toolbar Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Text(
                text = data.title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            LazyRow(
                modifier = Modifier.padding(16.dp)
            ) {
                items(data.genres.size) {
                    SuggestionChip(
                        onClick = {},
                        modifier = Modifier
                            .padding(start = 4.dp, end = 4.dp),
                        label = { Text(text = data.genres[it].name) },
                    )
                }
            }
            Text(
                text = data.overview,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Text(
                text = "Release Date: ${data.releaseDate}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Rating: ${data.voteAverage}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Popularity: ${data.popularity}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Vote Count: ${data.voteCount}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Original Language: ${data.originalLanguage}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Status: ${data.status}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Budget: ${data.budget}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Revenue: ${data.revenue}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
