package com.example.lab8_24531.locations

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lab8_24531.LocationDb

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleLugarsito(locationId: Int, onBackPressed: () -> Unit) {
    val db = remember { LocationDb() }
    val location = db.getLocationById(locationId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Location details") },
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("ID: ${location.id}", style = MaterialTheme.typography.bodyLarge)
            Text("Nombre: ${location.name}", style = MaterialTheme.typography.bodyLarge)
            Text("Tipo: ${location.type}", style = MaterialTheme.typography.bodyLarge)
            Text("Dimensión: ${location.dimension}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
