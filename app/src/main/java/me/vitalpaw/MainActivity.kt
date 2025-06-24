package me.vitalpaw

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.vitalpaw.ui.navigation.AppNavGraph


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController() // 👈 Dentro de setContent
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavGraph(navController = navController) // 👈 Ahora sí lo ve
                }
            }
        }
    }
}
