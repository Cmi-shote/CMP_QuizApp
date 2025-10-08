package com.example.quizapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import coil3.compose.rememberAsyncImagePainter
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import quizapp.composeapp.generated.resources.Res
import quizapp.composeapp.generated.resources.compose_multiplatform

data class QuizQuestionsScreenNav(val name: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        QuizQuestionsScreen(
            username = name,
            onQuizComplete = {
                navigator?.push(ResultScreenNav())
            }
        )
    }
}

@Composable
fun QuizQuestionsScreen(
    username: String,
    viewModel: QuizViewModel = koinViewModel(),
    onQuizComplete: () -> Unit = {}
) {
    val currentQuestion by viewModel.questions.collectAsState()
    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
    val selectedOption by viewModel.selectedOption.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val question = currentQuestion.getOrNull(currentIndex)
    val (progress, maxProgress) = viewModel.getProgress()

    LaunchedEffect(Unit) {
        viewModel.updateName(username)
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = error!!, color = Color.Red)
        }
    } else if (question != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = question.question,
                color = Color(0xFF363A43),
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            // Load flag image using Kamel
            Box(modifier = Modifier.background(Color.Gray)) {
                var imageLoadingResult by remember { mutableStateOf<Result<Painter>?>(null) }

                val painter = rememberAsyncImagePainter(
                    model = question.flagUrl,
                    onSuccess = {
                        imageLoadingResult = Result.success(it.painter)
                    },
                    onError = {
                        it.result.throwable.printStackTrace()
                        imageLoadingResult = Result.failure(it.result.throwable)
                    }
                )

                when(val result = imageLoadingResult) {
                    null -> CircularProgressIndicator()
                    else -> {
                        Image(
                            painter = if(result.isSuccess) painter else painterResource(Res.drawable.compose_multiplatform),
                            contentDescription = question.correctAnswer,
                            modifier = Modifier.size(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
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
                    modifier = Modifier.weight(1f),
                    color = ProgressIndicatorDefaults.linearColor,
                    trackColor = ProgressIndicatorDefaults.linearTrackColor,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                )

                Text(
                    text = "$progress/$maxProgress",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 15.dp)
                )
            }

            question.options.forEachIndexed { index, option ->
                val isSelected = selectedOption == index
                Text(
                    text = option,
                    color = if (isSelected) Color.White else Color(0xFF7A8089),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .border(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray, MaterialTheme.shapes.medium)
                        .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.White)
                        .clickable { viewModel.selectOption(index) }
                        .padding(15.dp),
                    textAlign = TextAlign.Start
                )
            }

            Button(
                onClick = {
                    viewModel.submitAnswer()
                    if (currentIndex == currentQuestion.size - 1) {
                        onQuizComplete()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (currentIndex == currentQuestion.size - 1) "FINISH" else "SUBMIT",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun QuizQuestionsScreenPreview() {
//    MaterialTheme {
//        QuizQuestionsScreen()
//    }
//}