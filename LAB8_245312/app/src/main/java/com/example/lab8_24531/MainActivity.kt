package com.example.lab8_24531

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.lab8_24531.ui.theme.LAB8_24531Theme
import com.example.lab8_24531.characterdetail.PantallaPJ
import com.example.lab8_24531.characterlist.ListaPJs
import com.example.lab8_24531.data.CharacterDb
import com.example.lab8_24531.login.LoginScreen
import com.example.lab8_24531.locations.ListadoLugares
import com.example.lab8_24531.locations.DetalleLugarsito
import com.example.lab8_24531.profile.PantPerfil


class MainActivity : ComponentActivity() {
    private val characterDb = CharacterDb()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LAB8_24531Theme {
                AppNavigation(characterDb)
            }
        }
    }
}

@Composable
fun AppNavigation(characterDb: CharacterDb) {
    val navController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute(navController)?.startsWith("characters") == true,
                    onClick = { navController.navigate("characters") { launchSingleTop = true } },
                    icon = { Icon(Icons.Default.Face, contentDescription = "Personajes") },
                    label = { Text("Personajes") }
                )
                NavigationBarItem(
                    selected = currentRoute(navController)?.startsWith("locations") == true,
                    onClick = { navController.navigate("locations") { launchSingleTop = true } },
                    icon = { Icon(Icons.Default.Place, contentDescription = "Lugares") },
                    label = { Text("Lugares") }
                )
                NavigationBarItem(
                    selected = currentRoute(navController) == "profile",
                    onClick = { navController.navigate("profile") { launchSingleTop = true } },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(padding)
        ) {
            composable("login") {
                LoginScreen(
                    onNavigateToCharacterList = {
                        navController.navigate("characters") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }

            navigation(startDestination = "characters/list", route = "characters") {
                composable("characters/list") {
                    BackHandler { (context as? Activity)?.finish() }
                    ListaPJs(
                        characters = characterDb.getAllCharacters(),
                        onCharacterClick = { id ->
                            navController.navigate("characters/detail/$id")
                        }
                    )
                }
                composable("characters/detail/{id}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")!!.toInt()
                    val character = characterDb.getCharacterById(id)
                    PantallaPJ(character = character, onBackPressed = { navController.popBackStack() })
                }
            }
            navigation(startDestination = "locations/list", route = "locations") {
                composable("locations/list") {
                    ListadoLugares(onLocationClick = { id ->
                        navController.navigate("locations/detail/$id")
                    })
                }
                composable("locations/detail/{id}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")!!.toInt()
                    DetalleLugarsito(locationId = id, onBackPressed = { navController.popBackStack() })
                }
            }

            composable("profile") {
                PantPerfil(
                    onLogout = {
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val destination = navController.currentDestination
    return destination?.hierarchy?.firstOrNull()?.route
}
