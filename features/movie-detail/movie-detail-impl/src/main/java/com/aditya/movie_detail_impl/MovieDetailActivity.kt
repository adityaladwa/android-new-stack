package com.aditya.movie_detail_impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
        enableEdgeToEdge()
        setContent {
            MovieDetailScreen(viewModel, movieId)
        }
    }
}
