package com.creativebird.puzzle.ui

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun TileGrid(
    tileOrder: List<Int>,
    imageParts: List<Bitmap>,
    draggedIndex: Int?,
    dropTargetIndex: Int?,
    tilePositions: MutableMap<Int, Offset>,
    tileSize: MutableState<IntSize>,
    dragOffset: Offset,
    onDragStart: (Int, Offset) -> Unit,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit,
    onDragCancel: () -> Unit
) {
    androidx.compose.foundation.layout.Column(
        modifier = Modifier.fillMaxSize()
    ) {
        for (row in 0..9) {
            val imageIndex = tileOrder[row]
            val isBeingDragged = draggedIndex == row
            val isDropTarget = dropTargetIndex == row && dropTargetIndex != draggedIndex

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .border(1.dp, Color.Red)
                    .onGloballyPositioned { coordinates ->
                        tilePositions[row] = coordinates.positionInWindow()
                        if (tileSize.value == IntSize.Zero) {
                            tileSize.value = coordinates.size
                        }
                    }
                    .pointerInput(row) {
                        detectDragGesturesAfterLongPress(
                            onDragStart = { touchOffset ->
                                // Touch-Position relativ zur Tile-Position
                                val tilePos = tilePositions[row] ?: Offset.Zero
                                val relativeOffset = Offset(
                                    touchOffset.x,
                                    touchOffset.y
                                )
                                onDragStart(row, relativeOffset)
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                onDrag(dragAmount)
                            },
                            onDragEnd = onDragEnd,
                            onDragCancel = onDragCancel
                        )
                    }
            ) {
                // Zeige das Bild nur wenn es nicht gezogen wird
                if (!isBeingDragged) {
                    TileImage(
                        bitmap = imageParts[imageIndex],
                        contentDescription = "Bildstreifen ${imageIndex + 1}"
                    )

                    // Graues Overlay wenn dies das Drop-Target ist
                    if (isDropTarget) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Gray.copy(alpha = 0.5f))
                        )
                    }
                }
            }
        }
    }
}