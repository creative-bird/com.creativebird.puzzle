package com.creativebird.puzzle.ui

import androidx.compose.ui.unit.IntOffset

// Berechnet den Offset für ein Tile basierend auf der Drag-Position mit Geschwindigkeitsbegrenzung
fun calculateTileOffsetWithEasing(
    currentIndex: Int,
    draggedIndex: Int,
    dropTargetIndex: Int,
    tileHeight: Int,
    dragOffset: Float
): IntOffset {
    var targetOffset = 0

    // Wenn das gedragte Tile nach unten bewegt wird
    if (dropTargetIndex > draggedIndex) {
        // Tiles zwischen draggedIndex und dropTargetIndex bewegen sich nach oben
        if (currentIndex > draggedIndex && currentIndex <= dropTargetIndex) {
            targetOffset = -tileHeight
        }
    }
    // Wenn das gedragte Tile nach oben bewegt wird
    else if (dropTargetIndex < draggedIndex) {
        // Tiles zwischen dropTargetIndex und draggedIndex bewegen sich nach unten
        if (currentIndex >= dropTargetIndex && currentIndex < draggedIndex) {
            targetOffset = tileHeight
        }
    }

    // Wenn kein Offset berechnet wurde, zurück
    if (targetOffset == 0) {
        return IntOffset.Zero
    }

    // Berechne die Bewegung basierend auf dem Drag-Progress
    // Nur die ersten 25% der Drag-Bewegung werden für die Animation verwendet
    val totalDragNeeded = tileHeight * 0.5f // 50% Schwellenwert
    val dragProgress = kotlin.math.abs(dragOffset) / totalDragNeeded

    // Nur die ersten 25% der Drag-Bewegung bewirken eine Animation
    val animationProgress = kotlin.math.min(dragProgress / 0.25f, 1f)

    // Berechne den aktuellen Offset basierend auf dem Progress
    val currentOffset = (targetOffset * animationProgress).toInt()

    return IntOffset(0, currentOffset)
}

// Original-Funktion für Kompatibilität behalten
fun calculateTileOffset(
    currentIndex: Int,
    draggedIndex: Int,
    dropTargetIndex: Int,
    tileHeight: Int
): IntOffset {
    // Wenn das gedragte Tile nach unten bewegt wird
    if (dropTargetIndex > draggedIndex) {
        // Tiles zwischen draggedIndex und dropTargetIndex bewegen sich nach oben
        if (currentIndex > draggedIndex && currentIndex <= dropTargetIndex) {
            return IntOffset(0, -tileHeight)
        }
    }
    // Wenn das gedragte Tile nach oben bewegt wird
    else if (dropTargetIndex < draggedIndex) {
        // Tiles zwischen dropTargetIndex und draggedIndex bewegen sich nach unten
        if (currentIndex >= dropTargetIndex && currentIndex < draggedIndex) {
            return IntOffset(0, tileHeight)
        }
    }

    return IntOffset.Zero
}