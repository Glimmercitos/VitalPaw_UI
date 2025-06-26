package me.vitalpaw.ui.screens.administrador

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vitalpaw.ui.components.admin.AdminAppoinmentCard
import me.vitalpaw.ui.navigation.veterinario.NavRoutes
import me.vitalpaw.viewmodels.veterinario.AppointmentViewModel

@Composable
fun AllAppointmentsScreen(
    navController: NavController,
    viewModel: AppointmentViewModel = hiltViewModel()){

    LaunchedEffect(Unit) {
        viewModel.loadAppointments()
    }

    val appointments = viewModel.appointments

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        if (appointments.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Sin citas asignadas0", style = MaterialTheme.typography.titleMedium)
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                appointments.forEach { appointment ->
                    AdminAppoinmentCard(
                        appointment = appointment
//                    onClick = {navController.navigate(NavRoutes.AppointmentDetail.createRoute(appointment.id!!)) }
                    )
                }
            }
        }
    }
    }
}

