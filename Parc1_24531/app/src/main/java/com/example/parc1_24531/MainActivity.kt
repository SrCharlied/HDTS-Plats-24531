package com.example.parc1_24531

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                App()
            }
        }
    }
}

sealed class Pantalla {
    data object Seleccion : Pantalla()
    data class Operacion(val tipo: String) : Pantalla()
}

@Composable
fun App() {
    var pantallaActual by remember { mutableStateOf<Pantalla>(Pantalla.Seleccion) }

    when (val pantalla = pantallaActual) {
        is Pantalla.Seleccion -> Pantalla1Screen(
            onOperacionSeleccionada = { op ->
                pantallaActual = Pantalla.Operacion(op)
            }
        )
        is Pantalla.Operacion -> Pantalla2Screen(pantalla.tipo) {
            pantallaActual = Pantalla.Seleccion
        }
    }
}

@Composable
fun Pantalla1Screen(onOperacionSeleccionada: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { onOperacionSeleccionada("Suma") }) {
            Text("SUMA")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onOperacionSeleccionada("Resta") }) {
            Text("RESTA")
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text("Carlos López - 24531", modifier = Modifier.offset(y=300.dp))
    }
}

@Composable
fun Pantalla2Screen(operacion: String, onVolver: () -> Unit) {
    var num1 by remember { mutableStateOf(TextFieldValue("")) }
    var num2 by remember { mutableStateOf(TextFieldValue("")) }
    var resultado by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Operación: $operacion")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = num1, onValueChange = { num1 = it }, label = { Text("Num1") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = num2, onValueChange = { num2 = it }, label = { Text("Num2") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val n1 = num1.text.toIntOrNull() ?: 0
            val n2 = num2.text.toIntOrNull() ?: 0
            resultado = if (operacion == "Suma") (n1 + n2).toString() else (n1 - n2).toString()
        }) {
            Text("OPERAR")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (resultado != null) {
            Text("Resultado: $resultado")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Pantalla1Preview() {
    MaterialTheme {
        Pantalla1Screen {}
    }
}

@Preview(showBackground = true)
@Composable
fun Pantalla2Preview() {
    MaterialTheme {
        Pantalla2Screen("Suma") {}
    }
}




