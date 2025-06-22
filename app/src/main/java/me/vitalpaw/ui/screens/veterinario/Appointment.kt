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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vitalpaw.ui.components.AppointmentCard
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.viewmodel.AppointmentViewModel
import android.util.Log

@Composable
fun AppointmentScreen(navController: NavController, token: String,viewModel: AppointmentViewModel = hiltViewModel()){
    LaunchedEffect(token) {
        Log.d("AppointmentScreen", "Loading appointments with token: $token")
        viewModel.loadAppointments(token)
    }


    val appointments = viewModel.appointments
    if (viewModel.error != null) {
        Text("Error: ${viewModel.error}")
    }


    Box(modifier = Modifier.fillMaxSize().background(Color.White)){
        if (appointments.isEmpty()){
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
                contentAlignment = Alignment.Center){
                Text(text = "Sin citas asignadas", style = MaterialTheme.typography.titleMedium)
            }
        }else{
            Column(modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(15.dp)) {
                appointments.forEach {
                    appointment -> AppointmentCard(
                        appointment = appointment,
                        onClick = {navController.navigate(NavRoutes.AppointmentDetail.createRoute(appointment.id, token))
                        }
                    )
                }
            }
        }
    }
}

