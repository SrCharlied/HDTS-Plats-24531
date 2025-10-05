package com.example.lab8_24531.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoLugares(
    onLocationClick: (Int) -> Unit,
    viewModel: ListadoLugaresViewM = viewModel()
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
                Spacer(Modifier.height(8.dp))
                Text("Cargando ubicaciones...")
            }
        }

        state.hasError -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Error al cargar ubicaciones", color = Color.Red)
                Spacer(Modifier.height(12.dp))
                Button(onClick = { viewModel.retry() }) {
                    Text("Reintentar")
                }
            }
        }

        else -> Scaffold(
            topBar = { TopAppBar(title = { Text("Locations") }) }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.data ?: emptyList()) { location ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLocationClick(location.id) },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(location.name, style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(4.dp))
                            Text("Tipo: ${location.type}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

