package me.vitalpaw.ui.screens.cliente

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vitalpaw.R
import me.vitalpaw.models.Appointment
import me.vitalpaw.ui.components.MyPetAppointmentCard
import me.vitalpaw.ui.components.buttons.AddAppointmentButton
import me.vitalpaw.ui.components.topbar.TopBar
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.cliente.MyAppointmentsPetViewModel

@Composable
fun MyPetAppointmentScreenContent(
    appointments: List<Appointment>,
    onBack: () -> Unit = {},
    onNewAppointment: () -> Unit = {},
    onCardClick: (Appointment) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Solo esta parte debe ser scrollable
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                TopBar(
                    title = "MIS CITAS",
                    onBackClick = onBack
                )

                if (appointments.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay citas registradas",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.LightGray
                        )
                    }
                } else {
                    appointments.forEach { appointment ->
                        MyPetAppointmentCard(
                            appointment = appointment,
                            onClick = { onCardClick(appointment) }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                Spacer(modifier = Modifier.height(100.dp)) // para que no lo tape el botón
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AddAppointmentButton(
                text = "Agendar nueva cita",
                onClick = onNewAppointment,
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .widthIn(min = 180.dp)
            )
        }
    }
}

@Composable
fun MyPetAppointmentScreen(
    navController: NavController,
    viewModel: MyAppointmentsPetViewModel = hiltViewModel()
) {
    val appointments = viewModel.appointments.collectAsState(initial = emptyList())

    MyPetAppointmentScreenContent(
        appointments = appointments.value,
        onBack = { navController.popBackStack() },
        onNewAppointment = {
            navController.navigate(NavRoutes.RegisterAppointment.route)
        },
        onCardClick = { appointment ->
            navController.navigate("pet_appointment_detail/${appointment.id}")
        }
    )
}
