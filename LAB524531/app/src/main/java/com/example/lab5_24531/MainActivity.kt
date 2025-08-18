package com.example.lab5_24531

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import com.example.lab5_24531.ui.theme.LAB524531Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LAB524531Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(MaterialTheme.colorScheme.background)

        ){}
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)

        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refrescar",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .offset(y=10.dp)
                    .size(50.dp)
            )
            Text(
                "Actualización disponible", color = MaterialTheme.colorScheme.onPrimaryContainer, fontSize = 15.sp,
                modifier = Modifier
                    .padding(22.dp)
            )
            Text(
                "Descargar", color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(22.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.cygames.umamusume"))
                        context.startActivity(intent)
                    }

            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(MaterialTheme.colorScheme.background)
        ){
            Column {
                Text("Lunes", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, fontSize = 50.sp, modifier=Modifier.padding(horizontal = 15.dp))
                Text("21 de Julio", color = MaterialTheme.colorScheme.onBackground, fontSize = 30.sp, modifier=Modifier.padding(horizontal = 15.dp))
            }
            Button(onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer,contentColor = MaterialTheme.colorScheme.onTertiaryContainer),
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .offset(y=40.dp)
                ){
                    Text("Terminar Jornada")
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(12.dp)
        ){
            Column(){
                Row(){
                    Column() {
                        Text("Kombu Ramen Shop", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                        Text("3rd Calle Oriente No. 19 D \nAntigua, Guatemala \n03001")
                        Text("12:00 pm a 10:00 pm")
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Ir al mapa",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(50.dp)
                            .offset(x=70.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.app.goo.gl/ddhGZfA9jNYbC5T7A"))
                                context.startActivity(intent)
                            }
                    )
                }
                Row(){
                    Button(onClick = {Toast.makeText(context,"Carlos André López Salazar",Toast.LENGTH_SHORT).show()},
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer,contentColor = MaterialTheme.colorScheme.onTertiaryContainer),
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                    ){
                        Text("Iniciar")
                    }
                    Button(onClick = {Toast.makeText(context,"Comida Japonesa \nEl valor economico es normal",Toast.LENGTH_SHORT).show()},
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer,contentColor = MaterialTheme.colorScheme.onTertiaryContainer),
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                    ){
                        Text("Detalles")
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LAB524531Theme {
        Greeting()
    }
}