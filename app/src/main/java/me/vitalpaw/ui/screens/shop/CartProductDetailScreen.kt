package me.vitalpaw.ui.screens.shop

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import me.vitalpaw.ui.components.buttons.RedeemPurchaseButton
import me.vitalpaw.ui.components.buttons.CancelarCitaButton
import me.vitalpaw.ui.navigation.NavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartProductDetailScreen(
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val cartItems by cartViewModel.cartItems.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo tipo drawable
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
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "CARRITO",
                                fontFamily = quicksandFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.Gray
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBack() }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Volver",
                                tint = Color.Black
                            )
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
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    if (cartItems.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Tu carrito está vacío",
                                    fontFamily = quicksandFont,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.LightGray
                                )
                            }
                        }
                    } else {
                        itemsIndexed(cartItems) { index, item ->
                            val product = item.first
                            val quantity = item.second

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp, horizontal = 8.dp)
                                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp))
                                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                                    .padding(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    // 📷 Imagen
                                    Image(
                                        painter = painterResource(id = product.imageResId),
                                        contentDescription = product.name,
                                        modifier = Modifier
                                            .size(90.dp)
                                            .padding(end = 12.dp)
                                    )

                                    // 📝 Info
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = product.name,
                                            fontFamily = quicksandFont,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Text(
                                            text = product.description,
                                            fontFamily = quicksandFont,
                                            fontSize = 14.sp,
                                            color = Color.Gray,
                                            lineHeight = 18.sp
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            IconButton(onClick = {
                                                if (quantity > 1)
                                                    cartViewModel.updateQuantity(
                                                        product,
                                                        quantity - 1
                                                    )
                                            }) {
                                                Icon(
                                                    Icons.Default.Remove,
                                                    contentDescription = "Restar"
                                                )
                                            }

                                            Text(
                                                text = quantity.toString(),
                                                fontFamily = quicksandFont,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )

                                            IconButton(onClick = {
                                                cartViewModel.updateQuantity(product, quantity + 1)
                                            }) {
                                                Icon(
                                                    Icons.Default.Add,
                                                    contentDescription = "Sumar"
                                                )
                                            }
                                        }
                                    }

                                    // 🗑 Eliminar
                                    IconButton(onClick = {
                                        cartViewModel.removeFromCart(product)
                                        Toast.makeText(
                                            context,
                                            "Producto eliminado",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Eliminar",
                                            tint = Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                ) {
                    RedeemPurchaseButton(onClick = {
                        navController.navigate(NavRoutes.CartRedeemDetail.route)
                    })
                }
            }
        }
    }
}



