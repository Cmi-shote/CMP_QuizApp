package com.example.quizapp.di

import com.example.quizapp.QuizViewModel
import com.example.quizapp.network.CountryApiService
import com.example.quizapp.network.createHttpClient
import com.example.quizapp.network.getHttpClient
import org.koin.dsl.module

val sharedModule = module {
    // global singleton instances
    single { createHttpClient(getHttpClient()) }
    single { CountryApiService(get()) }
    single { QuizViewModel(get()) }
}