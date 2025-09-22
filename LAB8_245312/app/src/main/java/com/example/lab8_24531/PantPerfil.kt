package com.example.lab8_24531.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lab8_24531.R

@Composable
fun PantPerfil(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.mi_avatar), 
            contentDescription = "Profile picture",
            modifier = Modifier.size(120.dp)
        )

        Spacer(Modifier.height(16.dp))
        Text("Carlos López", style = MaterialTheme.typography.headlineSmall)
        Text("Carné: 24531", style = MaterialTheme.typography.bodyMedium)

        Spacer(Modifier.height(24.dp))
        Button(onClick = onLogout) {
            Text("Cerrar sesión")
        }
    }
}
