package me.vitalpaw.ui.screens.shop

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vitalpaw.R
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.shop.CartViewModel
import me.vitalpaw.viewmodels.shop.HomeShopViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    shopViewModel: HomeShopViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    productIndex: Int,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val productos by shopViewModel.products.collectAsState()
    val producto = productos.getOrNull(productIndex)
    var quantity by remember { mutableStateOf(1) }

    producto?.let {
        Box(modifier = Modifier.fillMaxSize()) {
            // Fondo tipo drawable como en otras vistas
            Image(
                painter = painterResource(id = R.drawable.fondo),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                Text(
                                    text = "PRODUCTO",
                                    fontFamily = quicksandFont,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = Color.Black
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { onBack() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.Black)
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                    )
                },
                containerColor = Color.Transparent
            ) { padding ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = it.imageResId),
                                contentDescription = it.name,
                                modifier = Modifier
                                    .size(200.dp)
                                    .padding(vertical = 16.dp)
                            )

                            Text(
                                text = it.name,
                                fontFamily = quicksandFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.huellacoin),
                                    contentDescription = "Huella Coin",
                                    modifier = Modifier.size(20.dp)
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    text = "${it.points} pts",
                                    fontFamily = quicksandFont,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF005771)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = it.description,
                                fontFamily = quicksandFont,
                                fontSize = 15.sp,
                                color = Color.DarkGray
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = { if (quantity > 1) quantity-- }) {
                                        Icon(Icons.Default.Remove, contentDescription = "Restar")
                                    }

                                    Text(
                                        text = quantity.toString(),
                                        fontFamily = quicksandFont,
                                        fontSize = 20.sp
                                    )

                                    IconButton(onClick = { quantity++ }) {
                                        Icon(Icons.Default.Add, contentDescription = "Sumar")
                                    }
                                }

                                IconButton(
                                    onClick = {
                                        cartViewModel.addToCart(it, quantity)
                                        Toast.makeText(context, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(Color.White, shape = RoundedCornerShape(50))
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ShoppingCart,
                                        contentDescription = "Agregar al carrito",
                                        tint = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

