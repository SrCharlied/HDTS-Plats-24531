package com.example.lab8_24531.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun DetalleLugarsito(
    onBackPressed: () -> Unit,
    viewModel: DetalleLugarsitoViewM = viewModel()
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
                Text("Cargando ubicación...")
            }
        }

        state.hasError -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Error al cargar ubicación", color = Color.Red)
                Button(onClick = { viewModel.retry() }) {
                    Text("Reintentar")
                }
            }
        }

        else -> {
            val location = state.data!!
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Detalles de ubicación") },
                        navigationIcon = {
                            IconButton(onClick = onBackPressed) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("ID: ${location.id}", style = MaterialTheme.typography.bodyLarge)
                    Text("Nombre: ${location.name}", style = MaterialTheme.typography.bodyLarge)
                    Text("Tipo: ${location.type}", style = MaterialTheme.typography.bodyLarge)
                    Text("Dimensión: ${location.dimension}", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
