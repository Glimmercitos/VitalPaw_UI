@file:OptIn(ExperimentalMaterial3Api::class)

package me.vitalpaw.ui.screens.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.vitalpaw.R
import me.vitalpaw.ui.components.buttons.CancelarCitaButton
import me.vitalpaw.ui.components.buttons.GuardarCitaButton
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.shop.CartViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

val DeepBlue = Color(0xFF005771)

@Composable
fun ShopDetailScreen(
    navController: NavController,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    cartViewModel: CartViewModel,
    onBack: () -> Unit
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalPoints = cartItems.sumOf { it.first.points * it.second }
    val coinAmount = 2500

    val showDialog = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            topBar = {
                ShopDetailTopBar(coinAmount = coinAmount) {
                    navController.popBackStack()
                }
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Detalle de compra",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = quicksandFont,
                    color = DeepBlue
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        cartItems.forEach { (product, quantity) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${product.name} ($quantity)",
                                    fontFamily = quicksandFont,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "${product.points * quantity} pts",
                                    fontFamily = quicksandFont,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.huellacoin),
                        contentDescription = "Huella Coin",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Total de puntos: $totalPoints pts",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = quicksandFont,
                        color = DeepBlue
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CancelarCitaButton { onCancel() }
                    GuardarCitaButton {
                        showDialog.value = true
                        onConfirm()
                    }
                }
            }
        }

        // 🎉 Diálogo de confirmación de compra
        if (showDialog.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .width(280.dp)
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.check),
                            contentDescription = "Check",
                            modifier = Modifier.size(48.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Compra realizada con éxito",
                            fontFamily = quicksandFont,
                            fontSize = 18.sp,
                            color = Color(0xFF3695B9)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                showDialog.value = false
                                cartViewModel.clearCart()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3695B9)),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text("OK", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShopDetailTopBar(coinAmount: Int, onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 20.dp),
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
                    text = "COMPRA",
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
                }
            }
        }
    }
}

