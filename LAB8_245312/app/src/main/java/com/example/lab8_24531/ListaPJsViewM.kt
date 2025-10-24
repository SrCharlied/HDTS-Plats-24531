package com.example.lab8_24531.characterlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab8_24531.data.local.CharacterEntity
import com.example.lab8_24531.data.repository.CharacterRepository
import com.example.lab8_24531.viewmodel.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListaPJsViewM(application: Application) : AndroidViewModel(application) {

    private val repo = CharacterRepository(application)
    private val _state = MutableStateFlow(UiState<List<CharacterEntity>>())
    val state = _state.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            try {
                _state.value = UiState(isLoading = true)
                val characters = repo.getCharacters()
                _state.value = UiState(isLoading = false, data = characters)
            } catch (e: Exception) {
                _state.value = UiState(isLoading = false, hasError = true)
            }
        }
    }

    fun retry() = loadCharacters()
}

