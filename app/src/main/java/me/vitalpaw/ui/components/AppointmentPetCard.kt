package me.vitalpaw.ui.components

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.vitalpaw.models.Pet
import me.vitalpaw.R
import me.vitalpaw.ui.components.modal.MarkAsCompleteDialog

@Composable
fun AppointmentPetCard(
    pet: Pet,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val context = LocalContext.current
    val showDeleteDialogState = remember { mutableStateOf(false) }

    if (showDeleteDialogState.value) {
        MarkAsCompleteDialog(
            show = showDeleteDialogState.value,
            onDismiss = { showDeleteDialogState.value = false },
            onConfirm = {
                onDeleteClick()
                showDeleteDialogState.value = false
                Toast.makeText(context, "Perfil de mascota eliminado", Toast.LENGTH_SHORT).show()
            },
            title = "¿Eliminar mascota?",
            message = "Esta acción no se puede deshacer",
            confirmText = "Eliminar",
            dismissText = "Cancelar"
        )
    }

    Card(
        modifier = Modifier
            .width(370.dp)
            .padding(8.dp)
            .height(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(50.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.White)
                .border(2.dp, Color(0xFF3695B9), CircleShape)
        ) {
            // Imagen circular
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(85.dp)
                    .background(Color.White, CircleShape)
                    .padding(2.dp)
                    .clip(CircleShape)
            ) {
                Image(
                    painter = painterResource(pet.imageRes),
                    contentDescription = "Foto de ${pet.name}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = pet.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Text(
                    text = pet.breed,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            // 🗑️ Ícono de borrar con confirmación
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar mascota",
                tint = Color.Gray,
                modifier = Modifier.clickable {
                    showDeleteDialogState.value = true
                }
            )

            Spacer(modifier = Modifier.width(12.dp))

            // ➡️ Botón de flecha
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(55.dp)
                    .background(
                        Color(0xFF3695B9),
                        shape = RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)
                    ),
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
