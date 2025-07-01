@file:OptIn(ExperimentalMaterial3Api::class)

package me.vitalpaw.ui.screens.shop

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.vitalpaw.R
import me.vitalpaw.ui.components.ProductItem
import me.vitalpaw.ui.components.buttons.RedeemPurchaseButton
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.SessionViewModel
import me.vitalpaw.viewmodels.shop.HomeShopViewModel

@Composable
fun HomeShopScreen(
    navController: NavController,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    viewModel: HomeShopViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val token by sessionViewModel.firebaseToken.collectAsState()
    val productos by viewModel.products.collectAsState()
    val coinAmount by sessionViewModel.vitalCoins.collectAsState() // Puedes reemplazarlo por un valor del ViewModel si lo tienes.

    LaunchedEffect(token) {
        token?.let {
            Log.d("tienda", "Token recibido: $it")
            viewModel.loadCatalog(it)
            Log.d("TIENDA", "products en pantalla: ${productos.size}")
        }
    }
    Scaffold(
        containerColor = Color.White,
        topBar = {
            HomeShopTopBar(
                coinAmount = coinAmount,
                onBackClick = { navController.navigate(NavRoutes.HomeClient.route)},
                onCartClick = {
                    navController.navigate(NavRoutes.CartProductDetail.route)
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
            itemsIndexed(productos) { _, producto ->
                ProductItem(
                    image = producto.image,
                    name = producto.name,
                    price = producto.priceInVitalCoins,
                    onClick = {
                        navController.navigate(NavRoutes.ProductDetail.createRoute(producto._id))
                    }
                )
            }
        }
    }
}

@Composable
fun HomeShopTopBar(
    coinAmount: Int,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 24.dp, bottom = 8.dp), // MÁS ABAJO
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shadow(4.dp, RoundedCornerShape(50)),
            shape = RoundedCornerShape(50),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.Black
                    )
                }

                Text(
                    text = "TIENDA",
                    fontFamily = quicksandFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.Gray
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.huellacoin),
                        contentDescription = "Huella Coin",
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "%,d".format(coinAmount),
                        fontFamily = quicksandFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    IconButton(onClick = {
                        onCartClick() // ✅ CORRECTO: usa el callback en lugar de llamar a navController directamente
                    }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Ver carrito"
                        )
                    }
                }
            }
        }
    }
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun HomeShopScreenPreview() {
//    val nav = rememberNavController()
//    val previewViewModel = remember { HomeShopViewModel() }
//
//    HomeShopScreen(
//        navController = nav,
//        viewModel = previewViewModel,
//        onBack = {}
//    )
//}
