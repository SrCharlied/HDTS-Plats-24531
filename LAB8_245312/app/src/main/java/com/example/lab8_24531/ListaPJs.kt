package com.example.lab8_24531.characterlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaPJs(
    onCharacterClick: (Int) -> Unit,
    viewModel: ListaPJsViewM = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when {
        state.isLoading -> Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { viewModel.showError() },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Text("Cargando...")
            }
        }

        state.hasError -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Error al cargar personajes", color = Color.Red)
                Button(onClick = { viewModel.retry() }) {
                    Text("Reintentar")
                }
            }
        }

        else -> Scaffold(
            topBar = { TopAppBar(title = { Text("Characters") }) }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.data ?: emptyList()) { character ->
                    CuadritoPJ(
                        character = character,
                        onClick = { onCharacterClick(character.id) }
                    )
                }
            }
        }
    }
}