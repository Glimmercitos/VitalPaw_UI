package me.vitalpaw.ui.screens.cliente

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import me.vitalpaw.R
import me.vitalpaw.ui.theme.quicksandFont
import kotlinx.coroutines.delay


@Preview(showBackground = true)
@Composable
fun PreviewToAssigned() {
    HomeScreen()
}
@Composable
fun HomeScreen() {
    val images = listOf(
        R.drawable.promo1, // reemplaza con los nombres reales
        R.drawable.promo2,
        R.drawable.promo3,
        R.drawable.promo4
    )

    var currentImageIndex by remember { mutableStateOf(0) }

    // Carrusel automático
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentImageIndex = (currentImageIndex + 1) % images.size
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Título Promociones
        Text(
            text = "Promociones",
            fontFamily = quicksandFont,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF19486D),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Carrusel de imágenes
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = images[currentImageIndex]),
                contentDescription = "Promoción",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Título Servicios
        Text(
            text = "Nuestros Servicios",
            fontFamily = quicksandFont,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF19486D),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Servicios
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            ServiceItem(R.drawable.promo1)
            ServiceItem(R.drawable.promo2)
            ServiceItem(R.drawable.promo3)
            ServiceItem(R.drawable.promo4)
        }
    }
}

@Composable
fun ServiceItem(iconRes: Int) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = MaterialTheme.shapes.medium,
        color = Color(0xFFD3E5F5),
        shadowElevation = 6.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "Servicio",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
