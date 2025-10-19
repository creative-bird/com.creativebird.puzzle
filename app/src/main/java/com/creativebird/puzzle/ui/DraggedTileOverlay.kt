package com.creativebird.puzzle.ui

import android.graphics.Bitmap
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun DraggedTileOverlay(
    draggedIndex: Int?,
    tileOrder: List<Int>,
    imageParts: List<Bitmap>,
    tilePositions: Map<Int, Offset>,
    tileSize: IntSize,
    dragOffset: Offset,
    initialTouchOffset: Offset
) {
    draggedIndex?.let { index ->
        val imageIndex = tileOrder[index]

        if (tileSize != IntSize.Zero) {
            with(LocalDensity.current) {
                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                x = (index * 0 + dragOffset.x).roundToInt(),
                                y = (index * tileSize.height + dragOffset.y).roundToInt()
                            )
                        }
                        .size(
                            width = tileSize.width.toDp(),
                            height = tileSize.height.toDp()
                        )
                        .graphicsLayer {
                            shadowElevation = 16f
                            translationX = 6f
                            translationY = 10f
                        }
                        .border(1.dp, Color.LightGray)
                ) {
                    TileImage(
                        bitmap = imageParts[imageIndex],
                        contentDescription = "Gezogener Bildstreifen"
                    )
                }
            }
        }
    }
}