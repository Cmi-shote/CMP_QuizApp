package com.example.quizapp.util

import androidx.compose.ui.graphics.Color

// Color scheme for option states
data class OptionColors(
    val background: Color,
    val text: Color,
    val border: Color = Color.Gray
)

fun getOptionColors(
    isSelected: Boolean,
    isCorrect: Boolean,
    isSubmitted: Boolean
): OptionColors {
    return when {
        // After submission: show correct/incorrect
        isSubmitted && isCorrect -> OptionColors(
            background = Color(0xFF4CAF50),
            text = Color.White,
            border = Color(0xFF4CAF50)
        )
        isSubmitted && isSelected && !isCorrect -> OptionColors(
            background = Color(0xFFE53935),
            text = Color.White,
            border = Color(0xFFE53935)
        )
        isSubmitted -> OptionColors(
            background = Color.White,
            text = Color(0xFF7A8089),
            border = Color.LightGray
        )
        // Before submission: highlight selected option
        isSelected -> OptionColors(
            background = Color(0xFFBBDEFB), // More visible blue highlight
            text = Color(0xFF1B2430),
            border = Color(0xFF2196F3)
        )
        else -> OptionColors(
            background = Color.White,
            text = Color(0xFF7A8089),
            border = Color.LightGray
        )
    }
}