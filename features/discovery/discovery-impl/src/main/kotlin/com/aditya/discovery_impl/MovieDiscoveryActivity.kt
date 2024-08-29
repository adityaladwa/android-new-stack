package com.aditya.discovery_impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aditya.ui.theme.AndroidnewstackTheme

class MovieDiscoveryActivity : ComponentActivity() {

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
                    MovieDiscoveryScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MovieDiscoveryScreen(modifier: Modifier) {
    Text(
        text = "Movie discovery screen",
        modifier = modifier
    )
}