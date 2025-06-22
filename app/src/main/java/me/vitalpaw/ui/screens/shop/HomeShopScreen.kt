package me.vitalpaw.ui.screens.shop

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.vitalpaw.R
import me.vitalpaw.ui.components.ProductItem
import me.vitalpaw.ui.components.buttons.RedeemPurchaseButton
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.HomeShopViewModel

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
                        Text(
                            "TIENDA",
                            modifier = Modifier.weight(1f),
                            fontFamily = quicksandFont,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.huellacoin),
                            contentDescription = "Coin"
                        )
                        Text(
                            "2,500",
                            fontFamily = quicksandFont,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Carrito",
                            modifier = Modifier.padding(start = 8.dp)
                        )
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
            RedeemPurchaseButton(onClick = { /* Acción canjear */ })
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
    // Instancia directa del ViewModel con productos por defecto
    val previewViewModel = remember { HomeShopViewModel() }

    HomeShopScreen(
        viewModel = previewViewModel,
        onBack = {} // Acción vacía para preview
    )
}
