package com.creativebird.puzzle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class ImageOption(
    val id: Int,
    val resourceId: Int,
    val title: String
)

@Composable
fun ImageSelectionScreen(
    availableImages: List<Int>,
    artistName: String,
    onImageSelected: (Int) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val images = availableImages.mapIndexed { index, resourceId ->
        ImageOption(index + 1, resourceId, "Bild ${index + 1}")
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = artistName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(images) { image ->
                    ImageCard(
                        imageOption = image,
                        onClick = { onImageSelected(image.resourceId) }
                    )
                }
            }
        }

        // Zurück-Button oben rechts
        IconButton(
            onClick = onBackPressed,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Zurück",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ImageCard(
    imageOption: ImageOption,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Image(
            painter = painterResource(id = imageOption.resourceId),
            contentDescription = imageOption.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
    }
}