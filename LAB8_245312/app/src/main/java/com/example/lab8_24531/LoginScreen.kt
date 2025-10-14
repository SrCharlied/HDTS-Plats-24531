package com.example.lab8_24531.login

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab8_24531.R
import com.example.lab8_24531.data.UserPreferences
import com.example.lab8_24531.data.CharacterDb
import com.example.lab8_24531.LocationDb
import com.example.lab8_24531.data.local.AppDatabase
import com.example.lab8_24531.data.local.toEntity
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onNavigateToCharacterList: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userPrefs = remember { UserPreferences(context) }

    var name by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.rickymorty),
            contentDescription = "Logo de Rick y Morty",
            modifier = Modifier.size(300.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Ingresa tu nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    isLoading = true
                    userPrefs.saveUserName(name)
                    val db = AppDatabase.getDatabase(context)
                    val characters = CharacterDb().getAllCharacters().map { it.toEntity() }
                    val locations = LocationDb().getAllLocations().map { it.toEntity() }
                    db.characterDao().insertAll(characters)
                    db.locationDao().insertAll(locations)
                    kotlinx.coroutines.delay(4000)
                    isLoading = false
                    onNavigateToCharacterList()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = !isLoading && name.isNotBlank()
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text(
                    text = "Empezar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(200.dp))

        Text(
            text = "Carlos López - 24531",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}
