package com.example.lab8_24531.characterdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.lab8_24531.data.local.CharacterEntity
import com.example.lab8_24531.data.repository.CharacterRepository
import com.example.lab8_24531.viewmodel.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InfoPJsViewM(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val repository = CharacterRepository(application)
    private val id: Int = checkNotNull(savedStateHandle["id"])

    private val _state = MutableStateFlow(UiState<CharacterEntity>())
    val state = _state.asStateFlow()

    init {
        loadCharacter()
    }

    private fun loadCharacter() {
        viewModelScope.launch {
            _state.value = UiState(isLoading = true)
            try {
                val character = repository.getCharacterById(id)
                _state.value = UiState(isLoading = false, data = character)
            } catch (e: Exception) {
                _state.value = UiState(isLoading = false, hasError = true)
            }
        }
    }

    fun retry() = loadCharacter()
}

