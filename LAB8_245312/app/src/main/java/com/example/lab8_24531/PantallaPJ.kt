package com.example.lab8_24531.characterdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPJ(
    onBackPressed: () -> Unit,
    viewModel: InfoPJsViewM = viewModel()
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
                Spacer(modifier = Modifier.height(8.dp))
                Text("Cargando personaje...")
            }
        }

        state.hasError -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Error al cargar personaje", color = Color.Red)
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = { viewModel.retry() }) {
                    Text("Reintentar")
                }
            }
        }

        state.data != null -> {
            val character = state.data!!
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(character.name) },
                        navigationIcon = {
                            IconButton(onClick = onBackPressed) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(character.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = character.name,
                        modifier = Modifier
                            .size(180.dp)
                            .padding(bottom = 16.dp)
                    )

                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        InfoPJ("Especie:", character.species)
                        Spacer(modifier = Modifier.height(12.dp))
                        InfoPJ("Estado:", character.status)
                        Spacer(modifier = Modifier.height(12.dp))
                        InfoPJ("Género:", character.gender)
                    }
                }
            }
        }
    }
}

