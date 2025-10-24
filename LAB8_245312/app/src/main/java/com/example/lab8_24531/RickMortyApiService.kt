package com.example.lab8_24531.data.remote

import retrofit2.http.GET

interface RickMortyApiService {
    @GET("character")
    suspend fun getCharacters(): CharacterResponse

    @GET("location")
    suspend fun getLocations(): LocationResponse
}
