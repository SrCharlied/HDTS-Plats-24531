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
import androidx.navigation.toRoute
import com.example.lab8_24531.ui.theme.LAB8_24531Theme
import com.example.lab8_24531.characterdetail.PantallaPJ
import com.example.lab8_24531.characterlist.ListaPJs
import com.example.lab8_24531.data.CharacterDb
import com.example.lab8_24531.login.LoginScreen
import com.example.lab8_24531.locations.DetalleLugarsito
import com.example.lab8_24531.locations.ListadoLugares
import com.example.lab8_24531.profile.PantPerfil
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

@Serializable
data object CharactersDestination

@Serializable
data class CharacterDetailDestination(val id: Int)

@Serializable
data object LocationsDestination

@Serializable
data class LocationDetailDestination(val id: Int)

@Serializable
data object ProfileDestination

@Serializable
data object CharacterListRoute

@Serializable
data object LocationListRoute

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
                    selected = currentRoute(navController) == CharactersDestination::class.qualifiedName,
                    onClick = { navController.navigate(CharactersDestination) },
                    icon = { Icon(Icons.Default.Face, contentDescription = "Personajes") },
                    label = { Text("Personajes") }
                )
                NavigationBarItem(
                    selected = currentRoute(navController) == LocationsDestination::class.qualifiedName,
                    onClick = { navController.navigate(LocationsDestination) },
                    icon = { Icon(Icons.Default.Place, contentDescription = "Lugares") },
                    label = { Text("Lugares") }
                )
                NavigationBarItem(
                    selected = currentRoute(navController) == ProfileDestination::class.qualifiedName,
                    onClick = { navController.navigate(ProfileDestination) },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = LoginDestination,
            modifier = Modifier.padding(padding)
        ) {
            composable<LoginDestination> {
                LoginScreen(
                    onNavigateToCharacterList = {
                        navController.navigate(CharactersDestination) {
                            popUpTo(LoginDestination) { inclusive = true }
                        }
                    }
                )
            }

            navigation<CharactersDestination>(startDestination = CharacterListRoute) {
                composable<CharacterListRoute> {
                    BackHandler { (context as? Activity)?.finish() }
                    ListaPJs(
                        characters = characterDb.getAllCharacters(),
                        onCharacterClick = { id ->
                            navController.navigate(CharacterDetailDestination(id))
                        }
                    )
                }
                composable<CharacterDetailDestination> { backStackEntry ->
                    val destination = backStackEntry.toRoute<CharacterDetailDestination>()
                    val character = characterDb.getCharacterById(destination.id)
                    PantallaPJ(character = character, onBackPressed = { navController.popBackStack() })
                }
            }

            navigation<LocationsDestination>(startDestination = LocationListRoute) {
                composable<LocationListRoute> {
                    ListadoLugares(onLocationClick = { id ->
                        navController.navigate(LocationDetailDestination(id))
                    })
                }
                composable<LocationDetailDestination> { backStackEntry ->
                    val destination = backStackEntry.toRoute<LocationDetailDestination>()
                    DetalleLugarsito(locationId = destination.id, onBackPressed = { navController.popBackStack() })
                }
            }

            composable<ProfileDestination> {
                PantPerfil(
                    onLogout = {
                        navController.navigate(LoginDestination) {
                            popUpTo(LoginDestination) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    return navController.currentDestination?.route
}
