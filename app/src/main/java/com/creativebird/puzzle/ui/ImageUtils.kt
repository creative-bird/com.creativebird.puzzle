package com.creativebird.puzzle.ui

import android.graphics.Bitmap

// Funktion zum Aufteilen eines Bildes in 10 horizontale Streifen
fun splitImageInto10Rows(bitmap: Bitmap): List<Bitmap> {
    val parts = mutableListOf<Bitmap>()
    val height = bitmap.height / 10
    val width = bitmap.width

    for (row in 0..9) {
        val y = row * height
        val part = Bitmap.createBitmap(bitmap, 0, y, width, height)
        parts.add(part)
    }

    return parts
}