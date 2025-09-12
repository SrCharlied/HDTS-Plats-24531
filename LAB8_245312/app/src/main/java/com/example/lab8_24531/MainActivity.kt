package com.example.lab8_24531

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.lab8_24531.ui.theme.LAB8_24531Theme
import com.example.lab8_24531.login.LoginScreen
import com.example.lab8_24531.characterlist.ListaPJs
import com.example.lab8_24531.characterdetail.PantallaPJ
import com.example.lab8_24531.data.CharacterDb
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

@Serializable
data object CharacterListDestination

@Serializable
data class CharacterDetailDestination(val characterId: Int)

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

    NavHost(
        navController = navController,
        startDestination = LoginDestination
    ) {
        composable<LoginDestination> {
            LoginScreen(
                onNavigateToCharacterList = {
                    navController.navigate(CharacterListDestination) {
                        popUpTo(LoginDestination) { inclusive = true }
                    }
                }
            )
        }

        composable<CharacterListDestination> {
            BackHandler {
                (context as? Activity)?.finish()
            }

            ListaPJs(
                characters = characterDb.getAllCharacters(),
                onCharacterClick = { characterId ->
                    navController.navigate(CharacterDetailDestination(characterId))
                }
            )
        }

        composable<CharacterDetailDestination> { backStackEntry ->
            val destination = backStackEntry.toRoute<CharacterDetailDestination>()
            val character = characterDb.getCharacterById(destination.characterId)

            PantallaPJ(
                character = character,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}