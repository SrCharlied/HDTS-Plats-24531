package com.example.lab8_24531.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lab8_24531.LocationDb

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoLugares(onLocationClick: (Int) -> Unit) {
    val db = remember { LocationDb() }
    val locations = db.getAllLocations()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Locations") }) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(locations) { location ->
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
