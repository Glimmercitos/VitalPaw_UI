package me.vitalpaw.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.vitalpaw.viewmodels.ToAssignedViewModel

@Composable
fun ToAssignedScreen(viewModel: ToAssignedViewModel = ToAssignedViewModel()) {
    val pets = viewModel.petList
    val services = viewModel.serviceList

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Asignar Cita",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        pets.forEach { pet ->
            val service = services.find { it.petName == pet.petName }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Mascota: ${pet.petName}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("Dueño: ${pet.ownerName}")
                    Text("Tipo: ${pet.animalType}, Raza: ${pet.race}, Edad: ${pet.age}")
                    Text("Servicio: ${service?.type ?: "N/A"}")

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { /* Aquí podrías mostrar un picker en el futuro */ },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Asignar Cita", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }
}
