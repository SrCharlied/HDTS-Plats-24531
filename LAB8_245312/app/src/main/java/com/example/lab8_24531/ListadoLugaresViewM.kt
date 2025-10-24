package com.example.lab8_24531.locations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab8_24531.data.local.LocationEntity
import com.example.lab8_24531.data.repository.LocationRepository
import com.example.lab8_24531.viewmodel.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListadoLugaresViewM(application: Application) : AndroidViewModel(application) {
    private val repository = LocationRepository(application)

    private val _state = MutableStateFlow(UiState<List<LocationEntity>>())
    val state = _state.asStateFlow()

    init {
        loadLocations()
    }

    fun loadLocations() {
        viewModelScope.launch {
            _state.value = UiState(isLoading = true)
            try {
                val locations = repository.getAllLocations()
                _state.value = UiState(isLoading = false, data = locations)
            } catch (e: Exception) {
                _state.value = UiState(isLoading = false, hasError = true)
            }
        }
    }

    fun retry() = loadLocations()
}
