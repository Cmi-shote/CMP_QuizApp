package com.example.quizapp

import androidx.compose.ui.window.ComposeUIViewController
import com.example.quizapp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }