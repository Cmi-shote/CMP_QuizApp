package com.example.quizapp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GradientBackground(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        // Base vertical gradient
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF544FEE),
                    Color(0xFF7049F3),
                    Color(0xFF8C16FA)
                ),
                startY = 0f,
                endY = size.height
            )
        )

        // Top curved wave
        val topPath = Path().apply {
            moveTo(0f, size.height * 0.15f)
            cubicTo(
                size.width * 0.25f, size.height * 0.05f,
                size.width * 0.75f, size.height * 0.25f,
                size.width, size.height * 0.1f
            )
            lineTo(size.width, 0f)
            lineTo(0f, 0f)
            close()
        }
        drawPath(
            path = topPath,
            color = Color(0xFF6C63FF).copy(alpha = 0.4f)
        )

        // Bottom curved wave
        val bottomPath = Path().apply {
            moveTo(0f, size.height * 0.85f)
            cubicTo(
                size.width * 0.25f, size.height * 0.95f,
                size.width * 0.75f, size.height * 0.75f,
                size.width, size.height * 0.9f
            )
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        drawPath(
            path = bottomPath,
            color = Color(0xFF8E24AA).copy(alpha = 0.6f)
        )
    }
}

@Composable
@Preview
fun GradientBackgroundPreview() {
    GradientBackground()
}