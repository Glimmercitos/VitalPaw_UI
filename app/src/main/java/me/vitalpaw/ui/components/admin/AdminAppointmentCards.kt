package me.vitalpaw.ui.components.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import me.vitalpaw.models.Appointment


@Composable
fun AdminAppoinmentCard(
    appointment: Appointment,
//    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .width(340.dp)
            .padding(8.dp)
            .height(100.dp),
        shape = RoundedCornerShape(50.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row( //0xFF3695B9
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.White)
                .border(2.dp, Color(0xFF3695B9), CircleShape)
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(85.dp)
                    .background(Color.White, CircleShape)
                    .padding(2.dp)
                    .clip(CircleShape)
            ) {
                Image(
                    painter = painterResource(appointment.pet.imageRes ),
                    contentDescription = "Foto de ${appointment.pet.name}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 20.dp)
                    .padding(5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = appointment.pet.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Text(
                    text = "Fecha: ${appointment.date}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
                Text(
                    text = "Hora: ${appointment.time}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
//            Box(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .width(55.dp)
//                    .background(
//                        Color(0xFF6980BF),
//                        shape = RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)
//                    )
//                    .clickable { onClick() },
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    imageVector = Icons.Default.KeyboardArrowRight,
//                    contentDescription = "Ver detalles",
//                    tint = Color.White,
//                    modifier = Modifier.size(40.dp)
//                )
//            }
        }
    }
}