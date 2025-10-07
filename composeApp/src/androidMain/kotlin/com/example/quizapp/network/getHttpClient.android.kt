package com.example.quizapp.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual fun getHttpClient(): HttpClientEngine = OkHttp.create()