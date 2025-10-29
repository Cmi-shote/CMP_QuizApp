package com.example.quizapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.painterResource
import quizapp.composeapp.generated.resources.Res
import quizapp.composeapp.generated.resources.compose_multiplatform

@Composable
fun FlagImage(flagUrl: String, contentDescription: String) {
    Box(modifier = Modifier.background(Color.Gray)) {
        var imageLoadingResult by remember { mutableStateOf<Result<Painter>?>(null) }

        val painter = rememberAsyncImagePainter(
            model = flagUrl,
            onSuccess = {
                imageLoadingResult = Result.success(it.painter)
            },
            onError = {
                it.result.throwable.printStackTrace()
                imageLoadingResult = Result.failure(it.result.throwable)
            }
        )

        when (val result = imageLoadingResult) {
            null -> CircularProgressIndicator()
            else -> {
                Image(
                    painter = if (result.isSuccess) painter else painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = contentDescription,
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}