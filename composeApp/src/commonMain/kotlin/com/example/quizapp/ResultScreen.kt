package com.example.quizapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import quizapp.composeapp.generated.resources.Res
import quizapp.composeapp.generated.resources.ic_trophy

class ResultScreenNav : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        ResultScreen(
            onFinish = {
                navigator?.popUntilRoot()
            }
        )
    }
}

@Composable
fun ResultScreen(
    viewModel: QuizViewModel = koinViewModel(),
    onFinish: () -> Unit = {}
) {
    val score by viewModel.score.collectAsState()
    val totalQuestions = viewModel.questions.value.size
    val scoreText = "Your Score is $score out of $totalQuestions"
    val username by viewModel.name.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        GradientBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Result",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp)
            )

            Image(
                painter = painterResource(Res.drawable.ic_trophy), // Assuming ic_trophy is a drawable resource
                contentDescription = "Trophy",
                modifier = Modifier
                    .size(292.dp, 300.dp)
            )

            Text(
                text = "Congratulations",
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = username,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 25.dp)
            )

            Text(
                text = scoreText,
                color = Color.LightGray, // Using Gray as approximation for secondary_text_dark
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )

            Button(
                onClick = onFinish,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Finish",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun ResultScreenPreview() {
    MaterialTheme {
        ResultScreen()
    }
}