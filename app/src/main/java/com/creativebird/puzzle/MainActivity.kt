package com.creativebird.puzzle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.creativebird.puzzle.ui.ArtistSelectionScreen
import com.creativebird.puzzle.ui.ImageGrid
import com.creativebird.puzzle.ui.ImageSelectionScreen
import com.creativebird.puzzle.ui.SplashScreen
import com.creativebird.puzzle.ui.theme.CreativebirdTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CreativebirdTheme {
                var showSplash by remember { mutableStateOf(true) }
                var availableImages by remember { mutableStateOf<List<Int>?>(null) }
                var selectedImageId by remember { mutableStateOf<Int?>(null) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when {
                        // Screen 0: SplashScreen
                        showSplash -> {
                            SplashScreen(
                                onSplashFinished = {
                                    showSplash = false
                                }
                            )
                        }
                        // Screen 1: Künstlerauswahl
                        availableImages == null -> {
                            ArtistSelectionScreen(
                                onArtistSelected = { imageIds ->
                                    availableImages = imageIds
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        // Screen 2: Bildauswahl
                        selectedImageId == null -> {
                            ImageSelectionScreen(
                                availableImages = availableImages!!,
                                onImageSelected = { imageId ->
                                    selectedImageId = imageId
                                },
                                onBackPressed = {
                                    availableImages = null
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        // Screen 3: Puzzle-Grid
                        else -> {
                            ImageGrid(
                                imageResourceId = selectedImageId!!,
                                onBackPressed = {
                                    selectedImageId = null
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}