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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.lab8_24531.ui.theme.LAB8_24531Theme
import com.example.lab8_24531.characterdetail.PantallaPJ
import com.example.lab8_24531.characterlist.ListaPJs
import com.example.lab8_24531.data.UserPreferences
import com.example.lab8_24531.data.local.DatabasePopulator
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LAB8_24531Theme {
                AppNavigation()
            }
        }
        DatabasePopulator.populateDatabase(this)
    }
}



@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }
    val userName by userPrefs.userName.collectAsState(initial = null)

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isInLogin = currentDestination?.hierarchy?.any {
        it.route == LoginDestination::class.qualifiedName
    } == true

    if (userName == null) {
        Scaffold { padding ->
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
            }
        }
    } else {
        Scaffold(
            bottomBar = {
                if (!isInLogin) {
                    NavigationBar {
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any {
                                it.route == CharactersDestination::class.qualifiedName
                            } == true,
                            onClick = { navController.navigate(CharactersDestination) },
                            icon = { Icon(Icons.Default.Face, contentDescription = "Personajes") },
                            label = { Text("Personajes") }
                        )
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any {
                                it.route == LocationsDestination::class.qualifiedName
                            } == true,
                            onClick = { navController.navigate(LocationsDestination) },
                            icon = { Icon(Icons.Default.Place, contentDescription = "Lugares") },
                            label = { Text("Lugares") }
                        )
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any {
                                it.route == ProfileDestination::class.qualifiedName
                            } == true,
                            onClick = { navController.navigate(ProfileDestination) },
                            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                            label = { Text("Perfil") }
                        )
                    }
                }
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = CharactersDestination,
                modifier = Modifier.padding(padding)
            ) {
                navigation<CharactersDestination>(startDestination = CharacterListRoute) {
                    composable<CharacterListRoute> {
                        BackHandler { (context as? Activity)?.finish() }
                        ListaPJs(
                            onCharacterClick = { id ->
                                navController.navigate(CharacterDetailDestination(id))
                            }
                        )
                    }
                    composable<CharacterDetailDestination> { backStackEntry ->
                        PantallaPJ(onBackPressed = { navController.popBackStack() })
                    }
                }

                navigation<LocationsDestination>(startDestination = LocationListRoute) {
                    composable<LocationListRoute> {
                        ListadoLugares(onLocationClick = { id ->
                            navController.navigate(LocationDetailDestination(id))
                        })
                    }
                    composable<LocationDetailDestination> {
                        DetalleLugarsito(onBackPressed = { navController.popBackStack() })
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
}

