package com.example.lab8_24531.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab8_24531.data.Character
import com.example.lab8_24531.data.CharacterDb
import com.example.lab8_24531.viewmodel.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListaPJsViewM : ViewModel() {
    private val db = CharacterDb()
    private val _state = MutableStateFlow(UiState<List<Character>>())
    val state = _state.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _state.value = UiState(isLoading = true)
            delay(4000L)
            _state.value = UiState(isLoading = false, data = db.getAllCharacters())
        }
    }

    fun showError() {
        _state.update { it.copy(isLoading = false, hasError = true) }
    }

    fun retry() {
        loadCharacters()
    }
}
