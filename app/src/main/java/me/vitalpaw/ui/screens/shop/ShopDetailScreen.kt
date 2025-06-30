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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.vitalpaw.R
import me.vitalpaw.ui.components.buttons.CancelarCitaButton
import me.vitalpaw.ui.components.buttons.GuardarCitaButton
import me.vitalpaw.ui.components.buttons.RedeemPurchaseButton
import me.vitalpaw.ui.components.modal.ConfirmationDialog
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.shop.CartViewModel

val DeepBlue = Color(0xFF005771)

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

@Composable
fun ShopDetailScreen(
    navController: NavController,
    cartViewModel: CartViewModel,
    onBack: () -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalPoints = cartItems.sumOf { it.first.points * it.second }
    val coinAmount = 2500
    val remainingPoints = coinAmount - totalPoints
    val showDialog = remember { mutableStateOf(false) }

    // Log para verificar
    LaunchedEffect(cartItems) {
        println("🛒 Productos en ShopDetailScreen:")
        cartItems.forEach {
            println("Producto: ${it.first.name}, cantidad: ${it.second}")
        }
    }

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
                    onBack()
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
                        if (cartItems.isEmpty()) {
                            Text(
                                text = "No hay productos en el carrito",
                                fontFamily = quicksandFont,
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        } else {
                            cartItems.forEachIndexed { index, (product, quantity) ->
                                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                    Text(
                                        text = "Item ${index + 1}",
                                        fontFamily = quicksandFont,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color.DarkGray
                                    )
                                    Text(
                                        text = "Nombre: ${product.name}",
                                        fontFamily = quicksandFont,
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "Cantidad: $quantity",
                                        fontFamily = quicksandFont,
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "Subtotal: ${product.points * quantity} pts",
                                        fontFamily = quicksandFont,
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                    Divider(color = Color.LightGray, thickness = 1.dp)
                                }
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

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Puntos restantes: $remainingPoints pts",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = quicksandFont,
                    color = if (remainingPoints >= 0) DeepBlue else Color.Red
                )

                if (remainingPoints < 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Puntos insuficientes para realizar la compra.",
                        fontSize = 14.sp,
                        fontFamily = quicksandFont,
                        color = Color.Red
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CancelarCitaButton { onCancel() }

                    if (cartItems.isNotEmpty() && remainingPoints >= 0) {
                        Button(
                            onClick = {
                                showDialog.value = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF19486D)),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .width(140.dp)
                                .height(40.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 6.dp,
                                pressedElevation = 2.dp
                            )
                        ) {
                            Text(
                                text = "CANJEAR",
                                fontFamily = quicksandFont,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        if (showDialog.value) {
            ConfirmationDialog(
                show = true,
                onDismiss = {
                    cartViewModel.clearCart()    // 1. Limpia el carrito
                    showDialog.value = false     // 2. Cierra el diálogo
                },
                Title = "Compra realizada con éxito",
                Message = "Gracias por canjear tus VitalCoins"
            )
        }
    }
}
