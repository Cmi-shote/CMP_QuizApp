package com.example.quizapp

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val name: Name,
    val flags: Flags
)

@Serializable
data class Name(
    val common: String,
    val official: String
)

@Serializable
data class Flags(
    val png: String,
    val svg: String
)

@Serializable
data class CountryQuestion(
    val question: String,
    val flagUrl: String,
    val correctAnswer: String,
    val options: List<String>
)