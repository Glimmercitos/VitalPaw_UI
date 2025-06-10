package me.vitalpaw.ui.screens.veterinario

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.vitalpaw.ui.components.AppointmentCard
import me.vitalpaw.viewmodels.AppointmentViewModel
import me.vitalpaw.R


@Composable
fun AppointmentScreen(viewModel: AppointmentViewModel = viewModel()) {
    // Carga las citas al iniciar
    LaunchedEffect(Unit) {
        viewModel.loadAppointments()
    }

    val appointments = viewModel.appointments

    Box(modifier = Modifier.fillMaxSize()){
        if (appointments.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center)
            {
                Text(text = "Sin citas asignadas", style = MaterialTheme.typography.titleMedium)
            }
        } else {
            Column(modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(15.dp)) {
                appointments.forEach { appointment ->
                    AppointmentCard(
                        imageRes = appointment.imageRes,
                        petName = appointment.petName,
                        date = appointment.date,
                        time = appointment.time,
                        onClick = {
                        }
                    )
                }
            }
        }
    }
}
