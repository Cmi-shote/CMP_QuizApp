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
import com.example.quizapp.components.ActionButton
import com.example.quizapp.components.EmptyState
import com.example.quizapp.components.ErrorState
import com.example.quizapp.components.FlagImage
import com.example.quizapp.components.LoadingState
import com.example.quizapp.util.getOptionColors
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
    val questions by viewModel.questions.collectAsState()
    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
    val selectedOption by viewModel.selectedOption.collectAsState(initial = null)
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val isSubmitted by viewModel.isSubmitted.collectAsState(initial = false)

    val question = questions.getOrNull(currentIndex)
    val (progress, maxProgress) = viewModel.getProgress()

    LaunchedEffect(Unit) {
        viewModel.updateName(username)
    }

    when {
        isLoading -> LoadingState()
        error != null -> ErrorState(error!!)
        question == null -> EmptyState()
        else -> QuizContent(
            question = question,
            progress = progress,
            maxProgress = maxProgress,
            selectedOption = selectedOption,
            isSubmitted = isSubmitted,
            isLastQuestion = currentIndex == questions.size - 1,
            onSelectOption = viewModel::selectOption,
            onSubmit = viewModel::submitAnswer,
            onNext = {
                if (currentIndex == questions.size - 1) {
                    onQuizComplete()
                } else {
                    viewModel.nextQuestion()
                }
            }
        )
    }
}

@Composable
private fun QuizContent(
    question: CountryQuestion,
    progress: Int,
    maxProgress: Int,
    selectedOption: Int?,
    isSubmitted: Boolean,
    isLastQuestion: Boolean,
    onSelectOption: (Int) -> Unit,
    onSubmit: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        QuestionText(question.question)
        FlagImage(question.flagUrl, question.correctAnswer)
        ProgressSection(progress, maxProgress)
        OptionsSection(
            options = question.options,
            correctAnswer = question.correctAnswer,
            selectedOption = selectedOption,
            isSubmitted = isSubmitted,
            onSelectOption = onSelectOption
        )
        ActionButton(
            isSubmitted = isSubmitted,
            isLastQuestion = isLastQuestion,
            selectedOption = selectedOption,
            onSubmit = onSubmit,
            onNext = onNext
        )
    }
}

@Composable
private fun QuestionText(question: String) {
    Text(
        text = question,
        color = Color(0xFF363A43),
        fontSize = 22.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )
}

@Composable
private fun ProgressSection(progress: Int, maxProgress: Int) {
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
}

@Composable
private fun OptionsSection(
    options: List<String>,
    correctAnswer: String,
    selectedOption: Int?,
    isSubmitted: Boolean,
    onSelectOption: (Int) -> Unit
) {
    options.forEachIndexed { index, option ->
        val isSelected = selectedOption == index
        val isCorrect = option == correctAnswer
        val colors = getOptionColors(isSelected, isCorrect, isSubmitted)

        Text(
            text = option,
            color = colors.text,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .border(1.5.dp, colors.border, MaterialTheme.shapes.medium)
                .background(colors.background, MaterialTheme.shapes.medium)
                .clickable(enabled = !isSubmitted) { onSelectOption(index) }
                .padding(15.dp),
            textAlign = TextAlign.Start
        )
    }
}