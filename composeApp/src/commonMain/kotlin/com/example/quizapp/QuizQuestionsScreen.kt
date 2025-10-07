package com.example.quizapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import quizapp.composeapp.generated.resources.Res
import quizapp.composeapp.generated.resources.compose_multiplatform

@Composable
fun QuizQuestionsScreen(
    question: String = "What Country does this flag belong to",
    imageRes: Any? = null, // Placeholder for image resource
    progress: Int = 0,
    maxProgress: Int = 10,
    progressText: String = "0/10",
    options: List<String> = listOf("America", "China", "Uzbekistan", "Japan"),
    selectedOption: Int? = null,
    onOptionClick: (Int) -> Unit = {},
    onSubmit: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = question,
            color = Color(0xFF363A43),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        if (imageRes != null) {
            Image(
                painter = painterResource(Res.drawable.compose_multiplatform), // Placeholder, replace with actual image resource
                contentDescription = "Quiz Image",
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LinearProgressIndicator(
            progress = { progress.toFloat() / maxProgress },
            modifier = Modifier
                                .weight(1f),
            color = ProgressIndicatorDefaults.linearColor,
            trackColor = ProgressIndicatorDefaults.linearTrackColor,
            strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
            )

            Text(
                text = progressText,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start=15.dp)
            )
        }

        options.forEachIndexed { index, option ->
            Text(
                text = option,
                color = Color(0xFF7A8089),
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .border(1.dp, Color.Gray, MaterialTheme.shapes.medium) // Placeholder border
                    .background(Color.White)
                    .clickable { onOptionClick(index) }
                    .padding(15.dp),
                textAlign = TextAlign.Start
            )
        }

        Button(
            onClick = onSubmit,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "SUBMIT",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizQuestionsScreenPreview() {
    MaterialTheme {
        QuizQuestionsScreen()
    }
}