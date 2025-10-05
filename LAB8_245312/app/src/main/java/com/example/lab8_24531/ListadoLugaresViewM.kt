package com.example.lab8_24531.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab8_24531.Location
import com.example.lab8_24531.LocationDb
import com.example.lab8_24531.viewmodel.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListadoLugaresViewM : ViewModel() {
    private val db = LocationDb()
    private val _state = MutableStateFlow(UiState<List<Location>>())
    val state = _state.asStateFlow()

    init {
        loadLocations()
    }

    fun loadLocations() {
        viewModelScope.launch {
            _state.value = UiState(isLoading = true)
            delay(4000L)
            _state.value = UiState(isLoading = false, data = db.getAllLocations())
        }
    }

    fun showError() {
        _state.update { it.copy(isLoading = false, hasError = true) }
    }

    fun retry() {
        loadLocations()
    }
}