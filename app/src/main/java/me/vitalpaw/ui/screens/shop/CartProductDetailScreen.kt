package me.vitalpaw.ui.screens.shop

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.vitalpaw.models.Product
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartProductDetailScreen(
    navController: NavController,
    cartViewModel: CartViewModel,
    productIndex: Int,
    onBack: () -> Unit
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val context = LocalContext.current

    val productPair = cartItems.getOrNull(productIndex)
    val prod = productPair?.first
    val quantity = productPair?.second ?: 0

    // Si el producto ya no existe (por ejemplo, tras eliminarlo), navegar atrás
    if (productPair == null) {
        LaunchedEffect(Unit) {
            onBack()
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "CARRITO",
                            fontFamily = quicksandFont,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Producto
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
                    prod?.let {
                        Image(
                            painter = painterResource(id = it.imageResId),
                            contentDescription = it.name,
                            modifier = Modifier
                                .size(180.dp)
                                .padding(8.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = it.name,
                            fontFamily = quicksandFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = it.description,
                            fontFamily = quicksandFont,
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Cantidad: $quantity",
                            fontFamily = quicksandFont,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Total: ${it.points * quantity} pts",
                            fontFamily = quicksandFont,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF005771)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón eliminar
            Button(
                onClick = {
                    prod?.let { product ->
                        cartViewModel.removeFromCart(product)
                        Toast.makeText(context, "Producto eliminado del carrito", Toast.LENGTH_SHORT).show()
                        if (cartViewModel.cartItems.value.isEmpty()) {
                            navController.popBackStack()
                        } else {
                            onBack()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9534F)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ELIMINAR DEL CARRITO",
                    fontFamily = quicksandFont,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }
    }
}
