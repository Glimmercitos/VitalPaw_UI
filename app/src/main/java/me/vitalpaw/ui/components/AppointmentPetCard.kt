package me.vitalpaw.ui.components.cards

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import me.vitalpaw.models.Pet
import me.vitalpaw.R

@Composable
fun AppointmentPetCard(
    pet: Pet,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(RoundedCornerShape(50.dp))
            .border(2.dp, Color(0xFF4682B4), RoundedCornerShape(50.dp))
            .background(Color.White)
            .padding(start = 8.dp, end = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen circular
        Image(
            painter = painterResource(id = pet.imageRes),
            contentDescription = "Imagen de mascota",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Nombre y raza
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = pet.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = pet.breed,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        // Icono eliminar con Toast
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Eliminar mascota",
            tint = Color.Gray,
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    onDeleteClick()
                    Toast.makeText(context, "Perfil de mascota eliminado", Toast.LENGTH_SHORT).show()
                }
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Botón de flecha
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(55.dp)
                .background(
                    Color(0xFF4682B4),
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
