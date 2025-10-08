package com.example.quizapp.network

import com.example.quizapp.Country
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CountryApiService(private val httpClient: HttpClient) {

    suspend fun getAllCountries(): List<Country> {
        return httpClient.get("https://restcountries.com/v3.1/all?fields=name,flags").body()
    }
}