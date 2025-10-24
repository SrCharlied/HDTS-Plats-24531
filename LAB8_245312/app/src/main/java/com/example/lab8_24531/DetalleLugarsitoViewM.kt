package com.example.lab8_24531.locations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.lab8_24531.data.local.LocationEntity
import com.example.lab8_24531.data.repository.LocationRepository
import com.example.lab8_24531.viewmodel.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetalleLugarsitoViewM(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val repository = LocationRepository(application)
    private val id: Int = checkNotNull(savedStateHandle["id"])

    private val _state = MutableStateFlow(UiState<LocationEntity>())
    val state = _state.asStateFlow()

    init {
        loadLocation()
    }

    private fun loadLocation() {
        viewModelScope.launch {
            _state.value = UiState(isLoading = true)
            try {
                val location = repository.getLocationById(id)
                _state.value = UiState(isLoading = false, data = location)
            } catch (e: Exception) {
                _state.value = UiState(isLoading = false, hasError = true)
            }
        }
    }

    fun retry() = loadLocation()
}
