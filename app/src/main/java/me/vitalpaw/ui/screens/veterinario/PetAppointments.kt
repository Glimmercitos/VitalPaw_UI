package me.vitalpaw.ui.screens.veterinario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vitalpaw.ui.components.Vetcards.PetAppointmentCard
import me.vitalpaw.ui.components.buttons.SalirButton
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.MedicalRecordViewModel

@Composable
fun PetAppointmentsScreen(
    navController: NavController,
    petId: String,
    viewModel: MedicalRecordViewModel = hiltViewModel()
) {
    val medicalRecords = viewModel.getRecordsByPetId(petId)
    val petName = medicalRecords.firstOrNull()?.pet?.name ?: "Mascota"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Historial de citas de: $petName",
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = quicksandFont,
                modifier = Modifier.padding(vertical = 10.dp),
            )

            if (medicalRecords.isEmpty()) {
                Text("No hay citas anteriores para esta mascota.")
            } else {
                medicalRecords.forEach { record ->
                    PetAppointmentCard(
                        record = record,
                        onDetailsClick = {
                            navController.navigate(NavRoutes.PetAppointmentDetail.createRoute(record.appointment?.id!!))
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            SalirButton { navController.popBackStack() }
        }
    }
}

