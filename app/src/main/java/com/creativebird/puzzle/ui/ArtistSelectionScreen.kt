package com.creativebird.puzzle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.creativebird.puzzle.R

data class ArtistOption(
    val id: Int,
    val resourceId: Int,
    val name: String,
    val imageIds: List<Int>,
    val imageTexts: List<String>
)

@Composable
fun ArtistSelectionScreen(
    onArtistSelected: (List<Int>, List<String>, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val artists = listOf(
        ArtistOption(
            id = 1,
            resourceId = R.drawable.k1,
            name = "Künstler 1",
            imageIds = listOf(R.drawable.p1, R.drawable.p2, R.drawable.p3),
            imageTexts = listOf(
                "Die Kunst besteht etwas völlig verrücktes zu denken und in Verrückten etwas Sinnvolles zu sehen",
                "Kreativität ist Intelligenz die Spaß hat",
                "Kunst wäscht den Staub des Alltags von der Seele"
            )
        ),
        ArtistOption(
            id = 2,
            resourceId = R.drawable.k2,
            name = "Künstler 2",
            imageIds = listOf(R.drawable.p22, R.drawable.p33, R.drawable.p22),
            imageTexts = listOf(
                "Die Kunst besteht etwas völlig verrücktes zu denken und in Verrückten etwas Sinnvolles zu sehen",
                "Kreativität ist Intelligenz die Spaß hat",
                "Die Kunst besteht etwas völlig verrücktes zu denken und in Verrückten etwas Sinnvolles zu sehen"
            )
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAF9F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "KÜNSTLER",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(
                horizontal = LocalContext.current.resources.displayMetrics.widthPixels.times(0.1f).div(LocalContext.current.resources.displayMetrics.density).dp,
                vertical = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            items(artists) { artist ->
                ArtistCard(
                    artistOption = artist,
                    onClick = { onArtistSelected(artist.imageIds, artist.imageTexts, artist.name) }
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
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
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