package com.creativebird.puzzle.ui

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun ImageGrid(
    imageResourceId: Int,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Lade das komplette Bild mit der übergebenen Resource ID
    val fullBitmap = remember(imageResourceId) {
        BitmapFactory.decodeResource(context.resources, imageResourceId)
    }

    // Berechne das Aspect Ratio des Bildes
    val aspectRatio = remember(fullBitmap) {
        fullBitmap.width.toFloat() / fullBitmap.height.toFloat()
    }

    // Teile das Bild in 10 horizontale Streifen
    val imageParts = remember(fullBitmap) {
        splitImageInto10Rows(fullBitmap)
    }

    // State für die aktuelle Anordnung der Bildteile - durcheinander gemischt
    var tileOrder by remember(imageResourceId) { mutableStateOf((0..9).toList().shuffled()) }

    // State für Drag & Drop
    var draggedIndex by remember { mutableStateOf<Int?>(null) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var dropTargetIndex by remember { mutableStateOf<Int?>(null) }
    var initialTouchOffset by remember { mutableStateOf(Offset.Zero) }

    // Speichere Positionen und Größen der Tiles
    val tilePositions = remember { mutableMapOf<Int, Offset>() }
    val tileSize = remember { mutableStateOf(IntSize.Zero) }

    Box(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 0.1f.times(LocalContext.current.resources.displayMetrics.widthPixels).dp / LocalContext.current.resources.displayMetrics.density,
                    end = 0.1f.times(LocalContext.current.resources.displayMetrics.widthPixels).dp / LocalContext.current.resources.displayMetrics.density,
                    top = 0.1f.times(LocalContext.current.resources.displayMetrics.heightPixels).dp / LocalContext.current.resources.displayMetrics.density,
                    bottom = 0.1f.times(LocalContext.current.resources.displayMetrics.heightPixels).dp / LocalContext.current.resources.displayMetrics.density
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(aspectRatio)
            ) {
                // Grid mit allen Tiles
                TileGrid(
                    tileOrder = tileOrder,
                    imageParts = imageParts,
                    draggedIndex = draggedIndex,
                    dropTargetIndex = dropTargetIndex,
                    tilePositions = tilePositions,
                    tileSize = tileSize,
                    dragOffset = dragOffset,
                    onDragStart = { index, touchOffset ->
                        draggedIndex = index
                        dragOffset = Offset.Zero
                        dropTargetIndex = null
                        initialTouchOffset = touchOffset
                    },
                    onDrag = { dragAmount ->
                        dragOffset += dragAmount

                        // Berechne welches Tile zu 50% überdeckt ist
                        draggedIndex?.let { startIndex ->
                            val draggedTop = tilePositions[startIndex]!!.y + dragOffset.y
                            val draggedCenter = draggedTop + tileSize.value.height / 2f

                            // Finde das Tile, über dem sich die Mitte befindet
                            var targetIndex: Int? = null
                            for (i in 0..9) {
                                if (i != startIndex) {
                                    val tileTop = tilePositions[i]!!.y
                                    val tileBottom = tileTop + tileSize.value.height

                                    if (draggedCenter >= tileTop && draggedCenter < tileBottom) {
                                        targetIndex = i
                                        break
                                    }
                                }
                            }

                            dropTargetIndex = targetIndex
                        }
                    },
                    onDragEnd = {
                        // Verschiebe das Tile an die neue Position
                        draggedIndex?.let { startPos ->
                            dropTargetIndex?.let { targetPos ->
                                if (targetPos != startPos) {
                                    val newOrder = tileOrder.toMutableList()
                                    val draggedItem = newOrder.removeAt(startPos)
                                    newOrder.add(targetPos, draggedItem)
                                    tileOrder = newOrder

                                    // Prüfe ob das Puzzle gelöst ist
                                    if (newOrder == (0..9).toList()) {
                                        Toast.makeText(
                                            context,
                                            "Geschafft! Das Bild ist wieder vollständig!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        }

                        draggedIndex = null
                        dragOffset = Offset.Zero
                        dropTargetIndex = null
                        initialTouchOffset = Offset.Zero
                    },
                    onDragCancel = {
                        draggedIndex = null
                        dragOffset = Offset.Zero
                        dropTargetIndex = null
                        initialTouchOffset = Offset.Zero
                    }
                )

                // Gezogenes Tile über allem anderen
                DraggedTileOverlay(
                    draggedIndex = draggedIndex,
                    tileOrder = tileOrder,
                    imageParts = imageParts,
                    tilePositions = tilePositions,
                    tileSize = tileSize.value,
                    dragOffset = dragOffset,
                    initialTouchOffset = initialTouchOffset
                )
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