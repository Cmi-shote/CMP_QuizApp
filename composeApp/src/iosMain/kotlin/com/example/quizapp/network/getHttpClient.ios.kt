package com.example.quizapp.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual fun getHttpClient(): HttpClientEngine = Darwin.create()