package com.example.lab8_24531.locations

import androidx.lifecycle.SavedStateHandle
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


class DetalleLugarsitoViewM(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val db = LocationDb()
    private val locationId: Int = checkNotNull(savedStateHandle["id"])

    private val _state = MutableStateFlow(UiState<Location>())
    val state = _state.asStateFlow()

    init {
        loadLocation()
    }

    fun loadLocation() {
        viewModelScope.launch {
            _state.value = UiState(isLoading = true)
            delay(2000L)
            _state.value = UiState(isLoading = false, data = db.getLocationById(locationId))
        }
    }

    fun showError() {
        _state.update { it.copy(isLoading = false, hasError = true) }
    }

    fun retry() {
        loadLocation()
    }
}
