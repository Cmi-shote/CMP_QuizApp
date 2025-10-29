package com.example.quizapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(
    isSubmitted: Boolean,
    isLastQuestion: Boolean,
    selectedOption: Int?,
    onSubmit: () -> Unit,
    onNext: () -> Unit
) {
    Button(
        onClick = {
            if (!isSubmitted) {
                onSubmit()
            } else {
                onNext()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        enabled = selectedOption != null || isSubmitted,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        )
    ) {
        Text(
            text = when {
                !isSubmitted -> "SUBMIT"
                isLastQuestion -> "FINISH"
                else -> "NEXT"
            },
            fontWeight = FontWeight.Bold
        )
    }
}