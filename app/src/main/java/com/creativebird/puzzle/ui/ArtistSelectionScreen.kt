package com.creativebird.puzzle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.creativebird.puzzle.R

data class ArtistOption(
    val id: Int,
    val resourceId: Int,
    val name: String,
    val imageIds: List<Int>
)

@Composable
fun ArtistSelectionScreen(
    onArtistSelected: (List<Int>, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val artists = listOf(
        ArtistOption(
            id = 1,
            resourceId = R.drawable.k1,
            name = "Künstler 1",
            imageIds = listOf(R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p1, R.drawable.p2, R.drawable.p3)
        ),
        ArtistOption(
            id = 2,
            resourceId = R.drawable.k2,
            name = "Künstler 2",
            imageIds = listOf(R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22)
        ),
        ArtistOption(
            id = 3,
            resourceId = R.drawable.k1,
            name = "Künstler 3",
            imageIds = listOf(R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p1, R.drawable.p2, R.drawable.p3)
        ),
        ArtistOption(
            id = 4,
            resourceId = R.drawable.k2,
            name = "Künstler 4",
            imageIds = listOf(R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22)
        ),
        ArtistOption(
            id = 5,
            resourceId = R.drawable.k1,
            name = "Künstler 5",
            imageIds = listOf(R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p1, R.drawable.p2, R.drawable.p3)
        ),
        ArtistOption(
            id = 6,
            resourceId = R.drawable.k2,
            name = "Künstler 6",
            imageIds = listOf(R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22)
        ),
        ArtistOption(
            id = 7,
            resourceId = R.drawable.k1,
            name = "Künstler 7",
            imageIds = listOf(R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p1, R.drawable.p2, R.drawable.p3)
        ),
        ArtistOption(
            id = 8,
            resourceId = R.drawable.k2,
            name = "Künstler 8",
            imageIds = listOf(R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22)
        ),
        ArtistOption(
            id = 9,
            resourceId = R.drawable.k1,
            name = "Künstler 9",
            imageIds = listOf(R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p1, R.drawable.p2, R.drawable.p3)
        ),
        ArtistOption(
            id = 10,
            resourceId = R.drawable.k2,
            name = "Künstler 10",
            imageIds = listOf(R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22)
        ),
        ArtistOption(
            id = 11,
            resourceId = R.drawable.k1,
            name = "Künstler 11",
            imageIds = listOf(R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p1, R.drawable.p2, R.drawable.p3)
        ),
        ArtistOption(
            id = 12,
            resourceId = R.drawable.k2,
            name = "Künstler 12",
            imageIds = listOf(R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22)
        ),
        ArtistOption(
            id = 13,
            resourceId = R.drawable.k1,
            name = "Künstler 13",
            imageIds = listOf(R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p1, R.drawable.p2, R.drawable.p3)
        ),
        ArtistOption(
            id = 14,
            resourceId = R.drawable.k2,
            name = "Künstler 14",
            imageIds = listOf(R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22)
        ),
        ArtistOption(
            id = 15,
            resourceId = R.drawable.k1,
            name = "Künstler 15",
            imageIds = listOf(R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p1, R.drawable.p2, R.drawable.p3)
        ),
        ArtistOption(
            id = 16,
            resourceId = R.drawable.k2,
            name = "Künstler 16",
            imageIds = listOf(R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22, R.drawable.p33, R.drawable.p22)
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Künstler",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(artists) { artist ->
                ArtistCard(
                    artistOption = artist,
                    onClick = { onArtistSelected(artist.imageIds, artist.name) }
                )
            }
        }
    }
}

@Composable
fun ArtistCard(
    artistOption: ArtistOption,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = artistOption.resourceId),
                contentDescription = artistOption.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Text(
                text = artistOption.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}