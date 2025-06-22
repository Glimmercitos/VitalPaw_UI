package me.vitalpaw.ui.screens.veterinario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import me.vitalpaw.ui.components.Vetcards.AppointmentCard
import me.vitalpaw.ui.components.Vetcards.PetAppointmentCard
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodel.AppointmentViewModel

@Composable
fun PetAppointmentsScreen(
    navController: NavController,
    petId: String,
    viewModel: AppointmentViewModel = hiltViewModel()
) {
    val petAppointments = viewModel.getAppointmentByPetId(petId)
    val petName = petAppointments.firstOrNull()?.pet?.name ?: "Mascota"


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
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

        if (petAppointments.isEmpty()) {
            Text("No hay citas anteriores para esta mascota.")
        } else {
            petAppointments.forEach { appointment ->
                PetAppointmentCard(
                    appointment = appointment,
                    onDetailsClick = {
                        navController.navigate(NavRoutes.AppointmentDetail.createRoute(appointment.id!!))
                    }
                )
            }
        }
    }
}
