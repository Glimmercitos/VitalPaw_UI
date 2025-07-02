package me.vitalpaw

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.vitalpaw.ui.navigation.administrador.AdminNavGraph
//import me.vitalpaw.ui.navigation.veterinario.AppNavGraph
import me.vitalpaw.ui.screens.AssignVeterinarianScreen
import me.vitalpaw.ui.screens.administrador.AllVeterinarianScreen
import me.vitalpaw.ui.screens.administrador.RechargeVitalCoinsScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    AdminNavGraph(navController = navController)
                }
            }
        }
    }
}