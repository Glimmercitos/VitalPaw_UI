/*package me.vitalpaw.ui.screens.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.vitalpaw.R
import me.vitalpaw.ui.components.ProductItem
import me.vitalpaw.viewmodels.HomeShopViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeShopScreen(
    viewModel: HomeShopViewModel = viewModel(),
    onBack: () -> Unit
) {
    val productos by viewModel.products.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("TIENDA", modifier = Modifier.weight(1f))
                        Icon(painter = painterResource(id = R.drawable.huellacoin), contentDescription = "Coin")
                        Text("2,500", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 4.dp))
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", modifier = Modifier.padding(start = 8.dp))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = { /* Acción canjear */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6699CC))
            ) {
                Text("CANJEAR")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            itemsIndexed(productos) { index, producto ->
                ProductItem(
                    imageResId = producto.imageResId,
                    name = producto.name,
                    price = producto.price,
                    quantity = producto.quantity,
                    onIncrement = { viewModel.increment(index) },
                    onDecrement = { viewModel.decrement(index) }
                )
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeShopScreenPreview() {
    // Crea un NavHostController de prueba para el preview
    val navController = rememberNavController()

    HomeShopScreen(navController = NavHostController(LocalContext.current))
}
*/