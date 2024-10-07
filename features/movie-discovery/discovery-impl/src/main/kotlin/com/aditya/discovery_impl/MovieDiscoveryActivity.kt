package com.aditya.discovery_impl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aditya.movie_detail_api.MovieDetailNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDiscoveryActivity : ComponentActivity() {

    @Inject
    lateinit var movieDetailNavigator: MovieDetailNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieDiscoveryScreen(movieDetailNavigator)
        }
    }
}