package me.vitalpaw.ui.screens.veterinario

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vitalpaw.ui.components.Vetcards.AppointmentCard
import me.vitalpaw.ui.navigation.veterinario.NavRoutes
import me.vitalpaw.viewmodels.veterinario.AppointmentViewModel
import me.vitalpaw.ui.theme.quicksandFont
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import me.vitalpaw.ui.components.modal.MarkAsCompleteDialog

@Composable
fun AppointmentScreen(navController: NavController,viewModel: AppointmentViewModel = hiltViewModel()){
    LaunchedEffect(Unit) {
        viewModel.loadAppointments()
    }

    val appointments = viewModel.appointments
    var showDialog by remember { mutableStateOf(false) }
    var selectedAppointmentId by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)){
        if (appointments.isEmpty()){
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
                contentAlignment = Alignment.Center
            ){
                Text(text = "Sin citas asignadas0",
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = quicksandFont,
                    color = Color.Gray
                )
            }
        }else{
            Column(modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(15.dp)) {
                appointments.forEach {
                    appointment -> AppointmentCard(
                        appointment = appointment,
                        onClick = {navController.navigate(NavRoutes.AppointmentDetail.createRoute(appointment.id!!))
                        },
                        onHistoryClick = {navController.navigate(NavRoutes.PetAppointment.createRoute(appointment.pet.id!!))},
                        onCheckClick = { selectedAppointmentId = appointment.id
                            showDialog = true
                        }
                    )
                }
            }
        }
    }

    if (showDialog && selectedAppointmentId != null) {
        MarkAsCompleteDialog(
            show = showDialog,
            onDismiss = {
                showDialog = false
                selectedAppointmentId = null
            },
            onConfirm = {
                selectedAppointmentId?.let { viewModel.markAppointmentAsComplete(it) }
                showDialog = false
                selectedAppointmentId = null
            },
            title = "Marcar cita como atendida",
            message = "¿Seguro de marcar como atendida? La cita será eliminada.",
            confirmText = "Sí",
            dismissText = "No"
        )
    }
}

