package com.example.quizapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform