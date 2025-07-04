package me.vitalpaw.ui.components.Vetcards

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.vitalpaw.R
import me.vitalpaw.models.Appointment
import me.vitalpaw.utils.formatDate


@Composable
fun AppointmentCard(
    appointment: Appointment,
    onClick: () -> Unit,
    onHistoryClick: () -> Unit = {},
    onCheckClick:() -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(370.dp)
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

                AsyncImage(
                    model = appointment.pet.imageUrl.takeIf { it.isNotBlank() } ?: R.drawable.petphoto,
                    contentDescription = "Foto de ${appointment.pet.name}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }

            Column(
                modifier = Modifier.weight(1f). padding(start = 12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = appointment.pet.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.Black
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Fecha: ${formatDate(appointment.date)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = "Hora: ${appointment.time}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            Column(modifier = Modifier
                .padding(5.dp)
                .padding(end = 5.dp)
            ){
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "Historial",
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(24.dp)
                        .clickable { onHistoryClick() },
                    tint = Color(0xFF5E5E5E)
                )

                Spacer(modifier = Modifier.height(17.dp))

                Icon(
                    imageVector = Icons.Default.CheckCircleOutline,
                    contentDescription = "Completada",
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(24.dp)
                        .clickable { onCheckClick() },
                    tint = Color(0xFF5E5E5E)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(55.dp)
                    .background(
                        Color(0xFF3695B9),
                        shape = RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)
                    )
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Ver detalles",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

