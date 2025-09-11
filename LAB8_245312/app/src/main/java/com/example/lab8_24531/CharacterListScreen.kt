package com.example.lab8_24531.characterlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab8_24531.data.Character
import com.example.lab8_24531.data.CharacterDb
import com.example.lab8_24531.ui.theme.LAB8_24531Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    characters: List<Character>,
    onCharacterClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Characters") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(characters) { character ->
                CharacterListItem(
                    character = character,
                    onClick = { onCharacterClick(character.id) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterListScreenPreview() {
    LAB8_24531Theme {
        val db = CharacterDb()
        CharacterListScreen(
            characters = db.getAllCharacters().take(3),
            onCharacterClick = {}
        )
    }
}