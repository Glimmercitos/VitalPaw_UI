package me.vitalpaw

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.vitalpaw.ui.screens.shop.CartProductDetailScreen
import me.vitalpaw.viewmodels.shop.CartViewModel
import me.vitalpaw.models.Product
import me.vitalpaw.R

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                val navController = rememberNavController()
                val cartViewModel: CartViewModel = hiltViewModel()

                // Usar LaunchedEffect para evitar leer StateFlow.value directamente
                LaunchedEffect(Unit) {
                    if (cartViewModel.cartItems.value.isEmpty()) {
                        cartViewModel.addToCart(
                            product = Product(
                                id = 1,
                                name = "Pelota para Perro",
                                description = "Pelota resistente para juegos al aire libre.",
                                imageResId = R.drawable.prod8, // asegúrate de tener este recurso
                                points = 120
                            ),
                            quantity = 2
                        )
                    }
                }

                Surface(color = MaterialTheme.colorScheme.background) {
                    CartProductDetailScreen(
                        navController = navController,
                        cartViewModel = cartViewModel,
                        onBack = { navController.popBackStack() }
                    )
                }

        }
    }
}
