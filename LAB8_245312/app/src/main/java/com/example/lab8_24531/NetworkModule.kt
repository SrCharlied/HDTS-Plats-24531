package com.example.lab8_24531.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType


object NetworkModule {
    private val json = Json { ignoreUnknownKeys = true }

    val api: RickMortyApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RickMortyApiService::class.java)
    }
}
