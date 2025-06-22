package me.vitalpaw.ui.screens.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.vitalpaw.R
import me.vitalpaw.ui.components.ProductItem
import me.vitalpaw.ui.components.buttons.RedeemPurchaseButton
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.HomeShopViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeShopScreen(
    navController: NavController,
    viewModel: HomeShopViewModel = viewModel(),
    onBack: () -> Unit
) {
    val productos by viewModel.products.collectAsState()

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "TIENDA",
                            modifier = Modifier.weight(1f),
                            fontFamily = quicksandFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        Image(
                            painter = painterResource(id = R.drawable.huellacoin),
                            contentDescription = "Huella Coin",
                            modifier = Modifier.size(20.dp)
                        )

                        Text(
                            "2,500",
                            fontFamily = quicksandFont,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 4.dp)
                        )

                        IconButton(
                            onClick = {
                                navController.navigate(NavRoutes.Cart.route)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Carrito"
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {}
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
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

            item {
                Spacer(modifier = Modifier.height(16.dp))
                RedeemPurchaseButton(onClick = { /* acción canjear */ })
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeShopScreenPreview() {
    val nav = rememberNavController()
    val previewViewModel = remember { HomeShopViewModel() }

    HomeShopScreen(
        navController = nav,
        viewModel = previewViewModel,
        onBack = {}
    )
}
