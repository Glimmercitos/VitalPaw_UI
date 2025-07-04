package me.vitalpaw

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.vitalpaw.ui.navigation.AppNavGraph
import me.vitalpaw.ui.screens.Register
import me.vitalpaw.ui.screens.cliente.RegisterPetScreen
import me.vitalpaw.ui.screens.veterinario.ToAssigned
import me.vitalpaw.viewmodels.SessionViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val sessionViewModel: SessionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    AppNavGraph(navController = navController, sessionViewModel = sessionViewModel )
                }
            }
        }
    }
}