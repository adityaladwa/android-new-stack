package com.aditya.discovery_impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val result by viewModel.discoverMovies().collectAsStateWithLifecycle()
    Text(
        text = "Movie discovery screen",
        modifier = modifier
    )
}