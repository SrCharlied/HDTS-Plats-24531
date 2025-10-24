package com.example.lab8_24531.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)

@Serializable
data class LocationResponse(
    val results: List<LocationDto>
)
