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
import me.vitalpaw.ui.screens.cliente.PrimaryBlue
import me.vitalpaw.ui.screens.cliente.TextGray
import me.vitalpaw.ui.theme.quicksandFont

val DeepBlue = Color(0xFF005771)

@Composable
fun ShopDetailScreen(
    navController: NavController,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    val items = listOf(
        "Arena para gato (2)" to 1600,
        "Juguete para ave (1)" to 125
    )
    val total = items.sumOf { it.second }
    val coinAmount = 2500

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Fondo drawable
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
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        items.forEachIndexed { index, (name, points) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = name,
                                    fontFamily = quicksandFont,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "$points pts",
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
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total de puntos: ",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = quicksandFont,
                        color = DeepBlue
                    )
                    Text(
                        text = "$total pts",
                        fontSize = 16.sp,
                        fontFamily = quicksandFont,
                        color = TextGray
                    )
                }

                Spacer(modifier = Modifier.weight(1f)) // Mueve los botones hacia abajo

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onCancel,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C7B6F)),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "CANCELAR",
                            fontFamily = quicksandFont,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(24.dp))

                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "CONFIRMAR",
                            fontFamily = quicksandFont,
                            fontSize = 14.sp,
                            color = Color.White
                        )
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.huellacoin),
                        contentDescription = "Huella Coin",
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    text = "COMPRA",
                    fontFamily = quicksandFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.Gray
                )

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
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShopDetailScreenPreview() {
    val nav = rememberNavController()
    ShopDetailScreen(
        navController = nav,
        onCancel = {},
        onConfirm = {}
    )
}
