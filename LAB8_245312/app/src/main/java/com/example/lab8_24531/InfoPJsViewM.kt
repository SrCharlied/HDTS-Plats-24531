package com.example.lab8_24531.characterdetail

import androidx.lifecycle.SavedStateHandle
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

class InfoPJsViewM(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val db = CharacterDb()
    private val characterId: Int = checkNotNull(savedStateHandle["id"])

    private val _state = MutableStateFlow(UiState<Character>())
    val state = _state.asStateFlow()

    init {
        loadCharacter()
    }

    fun loadCharacter() {
        viewModelScope.launch {
            _state.value = UiState(isLoading = true)
            delay(2000L)
            _state.value = UiState(isLoading = false, data = db.getCharacterById(characterId))
        }
    }

    fun showError() {
        _state.update { it.copy(isLoading = false, hasError = true) }
    }

    fun retry() {
        loadCharacter()
    }
}
