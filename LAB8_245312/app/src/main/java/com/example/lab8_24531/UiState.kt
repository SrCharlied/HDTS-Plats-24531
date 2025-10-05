package com.example.lab8_24531.viewmodel

data class UiState<T>(
    val isLoading: Boolean = true,
    val data: T? = null,
    val hasError: Boolean = false
)
