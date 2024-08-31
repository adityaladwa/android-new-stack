package com.aditya.movie_detail_impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
                Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                    MovieDiscoveryScreen(Modifier.padding(innerPadding), viewModel, movieId)
                }
            }
        }
    }
}

@Composable
fun MovieDiscoveryScreen(
    modifier: Modifier,
    viewModel: MovieDetailViewModel,
    movieId: Int
) {
    val movieDetailFlow = remember { viewModel.movieDetailsFlow(movieId) }
    val result = movieDetailFlow.collectAsStateWithLifecycle()
}