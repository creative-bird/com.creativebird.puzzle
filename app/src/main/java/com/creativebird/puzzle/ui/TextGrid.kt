package com.creativebird.puzzle.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import com.creativebird.puzzle.R

@Composable
fun TextGrid(
    text: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val density = LocalDensity.current

    // Lade die Kirang Haerang Font
    val typeface = remember {
        ResourcesCompat.getFont(context, R.font.kiranghaerang_regular) ?: Typeface.DEFAULT
    }

    // Erstelle das Text-Bitmap
    val textBitmap = remember(text) {
        createTextBitmap(
            text = text,
            typeface = typeface,
            textSizePx = with(density) { 24.dp.toPx() }, // headlineSmall Größe
            width = 1080, // Standard Breite
            textColor = androidx.compose.ui.graphics.Color.Black.toArgb(),
            backgroundColor = androidx.compose.ui.graphics.Color.White.toArgb()
        )
    }

    // Berechne das Aspect Ratio des Text-Bildes
    val aspectRatio = remember(textBitmap) {
        textBitmap.width.toFloat() / textBitmap.height.toFloat()
    }

    // Teile das Text-Bild in Zeilen basierend auf Satzhöhe
    val textParts = remember(textBitmap) {
        splitTextBitmapByLines(textBitmap, with(density) { 24.dp.toPx() })
    }

    // State für die aktuelle Anordnung - durcheinander gemischt
    var tileOrder by remember(text) {
        mutableStateOf((0 until textParts.size).toList().shuffled())
    }

    // State für Drag & Drop
    var draggedIndex by remember { mutableStateOf<Int?>(null) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var dropTargetIndex by remember { mutableStateOf<Int?>(null) }
    var initialTouchOffset by remember { mutableStateOf(Offset.Zero) }

    // Speichere Positionen und Größen der Tiles
    val tilePositions = remember { mutableMapOf<Int, Offset>() }
    val tileSize = remember { mutableStateOf(IntSize.Zero) }

    Box(modifier = modifier
        .fillMaxSize()
        .background(Color(0xD5CFB7FF))
    ) {
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
                // Grid mit allen Text-Tiles
                TextTileGrid(
                    tileOrder = tileOrder,
                    textParts = textParts,
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

                        draggedIndex?.let { startIndex ->
                            if (tilePositions.isNotEmpty() && tileSize.value != IntSize.Zero) {
                                val draggedTop = tilePositions[startIndex]!!.y + dragOffset.y
                                val draggedCenter = draggedTop + tileSize.value.height / 2f

                                var targetIndex: Int? = null
                                for (i in 0 until textParts.size) {
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
                        }
                    },
                    onDragEnd = {
                        draggedIndex?.let { startPos ->
                            dropTargetIndex?.let { targetPos ->
                                if (targetPos != startPos) {
                                    val newOrder = tileOrder.toMutableList()
                                    val draggedItem = newOrder.removeAt(startPos)
                                    newOrder.add(targetPos, draggedItem)
                                    tileOrder = newOrder
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

                // Gezogenes Text-Tile über allem anderen
                DraggedTileOverlay(
                    draggedIndex = draggedIndex,
                    tileOrder = tileOrder,
                    imageParts = textParts,
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

// Funktion zum Erstellen eines Bitmaps mit Text
fun createTextBitmap(
    text: String,
    typeface: Typeface,
    textSizePx: Float,
    width: Int,
    textColor: Int,
    backgroundColor: Int
): Bitmap {
    val paint = Paint().apply {
        this.typeface = typeface
        this.textSize = textSizePx
        this.color = textColor
        isAntiAlias = true
    }

    // Berechne Zeilenhöhe
    val fontMetrics = paint.fontMetrics
    val lineHeight = (fontMetrics.descent - fontMetrics.ascent + fontMetrics.leading).toInt()
    val padding = 40 // Padding oben/unten

    // Teile Text in Zeilen auf
    val words = text.split(" ")
    val lines = mutableListOf<String>()
    var currentLine = ""

    for (word in words) {
        val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"
        val lineWidth = paint.measureText(testLine)

        if (lineWidth > width - 80) { // 40px padding links und rechts
            if (currentLine.isNotEmpty()) {
                lines.add(currentLine)
                currentLine = word
            } else {
                lines.add(word)
            }
        } else {
            currentLine = testLine
        }
    }
    if (currentLine.isNotEmpty()) {
        lines.add(currentLine)
    }

    // Berechne Bitmap-Höhe
    val height = lines.size * lineHeight + padding * 2

    // Erstelle Bitmap
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawColor(backgroundColor)

    // Zeichne Text
    var y = padding - fontMetrics.ascent
    for (line in lines) {
        canvas.drawText(line, 40f, y, paint)
        y += lineHeight
    }

    return bitmap
}

// Funktion zum Aufteilen des Text-Bitmaps in Zeile
fun splitTextBitmapByLines(bitmap: Bitmap, lineHeightPx: Float): List<Bitmap> {
    val parts = mutableListOf<Bitmap>()
    val lineHeight = lineHeightPx.toInt()
    val width = bitmap.width
    val totalHeight = bitmap.height

    var y = 0
    while (y < totalHeight) {
        val remainingHeight = totalHeight - y
        val currentHeight = minOf(lineHeight, remainingHeight)

        if (currentHeight > 0) {
            val part = Bitmap.createBitmap(bitmap, 0, y, width, currentHeight)
            parts.add(part)
        }

        y += lineHeight
    }

    return parts
}
