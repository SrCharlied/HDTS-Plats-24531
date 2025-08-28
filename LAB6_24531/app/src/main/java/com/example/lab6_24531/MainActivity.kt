package com.example.lab6_24531

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab6_24531.ui.theme.LAB6_24531Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LAB6_24531Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CounterApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CounterApp(modifier: Modifier = Modifier) {
    var counter by remember { mutableStateOf(0) }
    var totalIncrements by remember { mutableStateOf(0) }
    var totalDecrements by remember { mutableStateOf(0) }
    var maxValue by remember { mutableStateOf(0) }
    var minValue by remember { mutableStateOf(0) }
    var totalChanges by remember { mutableStateOf(0) }
    var history by remember { mutableStateOf(listOf<Pair<Int, Boolean>>()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Carlos André López Salazar",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    counter--
                    totalDecrements++
                    totalChanges++
                    minValue = if (totalChanges == 1) counter else minOf(minValue, counter)
                    history = history + (counter to false)
                },
                shape = CircleShape,
                modifier = Modifier.size(50.dp)
            ) {
                Text("-", fontSize = 20.sp)
            }

            Text(
                text = "$counter",
                fontSize = 40.sp,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Button(
                onClick = {
                    counter++
                    totalIncrements++
                    totalChanges++
                    maxValue = if (totalChanges == 1) counter else maxOf(maxValue, counter)
                    history = history + (counter to true)
                },
                shape = CircleShape,
                modifier = Modifier.size(50.dp)
            ) {
                Text("+", fontSize = 20.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text("Total incrementos: $totalIncrements")
            Text("Total decrementos: $totalDecrements")
            Text("Valor máximo: $maxValue")
            Text("Valor mínimo: $minValue")
            Text("Total cambios: $totalChanges")
            Text("Historial:", modifier = Modifier.padding(top = 8.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(history) { (value, isIncrement) ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(50.dp)
                        .background(
                            color = if (isIncrement) Color(0xFF2E7D32) else Color(0xFFC62828),
                            shape = MaterialTheme.shapes.medium
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = value.toString(),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Button(
            onClick = {
                counter = 0
                totalIncrements = 0
                totalDecrements = 0
                maxValue = 0
                minValue = 0
                totalChanges = 0
                history = emptyList()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Reiniciar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterAppPreview() {
    LAB6_24531Theme {
        CounterApp()
    }
}
