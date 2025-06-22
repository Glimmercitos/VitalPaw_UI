@file:OptIn(ExperimentalMaterial3Api::class)

package me.vitalpaw.ui.screens.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.vitalpaw.R
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip

@Composable
fun ShopScreen(navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo VitalPaw",
                modifier = Modifier
                    .height(150.dp)
                    .padding(bottom = 24.dp)
            )

            // ZONA VISUAL DE LAS 3 IMÁGENES
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentAlignment = Alignment.Center
            ) {
                // pub1 a la izquierda con padding extra
                Image(
                    painter = painterResource(id = R.drawable.pub1),
                    contentDescription = "Imagen Izquierda",
                    modifier = Modifier
                        .height(160.dp)
                        .width(120.dp)
                        .align(Alignment.CenterStart)
                        .padding(end = 24.dp),
                    contentScale = ContentScale.Crop
                )

                // pub3 a la derecha con padding extra
                Image(
                    painter = painterResource(id = R.drawable.pub3),
                    contentDescription = "Imagen Derecha",
                    modifier = Modifier
                        .height(160.dp)
                        .width(120.dp)
                        .align(Alignment.CenterEnd)
                        .padding(start = 24.dp),
                    contentScale = ContentScale.Crop
                )

                // pub2 en el centro, encima y más grande, con mejor sombra
                Image(
                    painter = painterResource(id = R.drawable.pub2),
                    contentDescription = "Imagen Centro",
                    modifier = Modifier
                        .height(234.dp)
                        .width(129.dp)
                        .align(Alignment.Center)
                        .offset(y = 120.dp)
                        .graphicsLayer {
                            shadowElevation = 12.dp.toPx()
                            shape = RoundedCornerShape(12.dp)
                            clip = true
                        },
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(150.dp))

            Text(
                text = "HECHO CON AMOR PARA TUS\nMASCOTAS",
                fontSize = 20.sp,
                fontFamily = quicksandFont,
                textAlign = TextAlign.Center
            )
        }
    }
}