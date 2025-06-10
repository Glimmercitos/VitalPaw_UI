package me.vitalpaw.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vitalpaw.R


@Composable
fun AppointmentCard(
    imageRes: Int,
    petName: String,
    date: String,
    time: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(370.dp)
            .padding(8.dp)
            .height(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(50.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row( //0xFF4AA5C8
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.White)
                .border(2.dp, Color(0xFF3695B9), CircleShape)
        ) {
            // Imagen circular
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(85.dp) // Tamaño total incluyendo el borde
                    .background(Color.White, CircleShape)
                    .padding(2.dp) // Espacio para que la imagen no tape el borde
                    .clip(CircleShape)
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Foto de $petName",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }


            // Contenido central
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = petName,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Text(
                    text = "fecha: $date",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
                Text(
                    text = "hora: $time",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }

            // Botón con flecha
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(55.dp)
                    .background(Color(0xFF3695B9),
                        shape = RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Ver detalles",
                    tint = Color.White,
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppointmentPreview() {
    AppointmentCard(
        imageRes = R.drawable.ic_launcher_background,
        petName = "Max",
        date = "28 de mayo",
        time = "10:00 AM",
        onClick = {}
    )
}