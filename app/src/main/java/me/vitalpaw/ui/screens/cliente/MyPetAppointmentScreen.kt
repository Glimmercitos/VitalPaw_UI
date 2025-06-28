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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vitalpaw.R
import me.vitalpaw.models.Appointment
import me.vitalpaw.models.Pet
import me.vitalpaw.models.User
import me.vitalpaw.ui.components.MyPetAppointmentCard
import me.vitalpaw.ui.components.buttons.AddAppointmentButton
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodel.MyAppointmentsPetViewModel

@Composable
fun MyPetAppointmentScreenContent(
    appointments: List<Appointment>,
    onBack: () -> Unit = {},
    onNewAppointment: () -> Unit = {},
    onCardClick: (Appointment) -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp), // separacion abajo
                contentAlignment = Alignment.Center
            ) {
                AddAppointmentButton(
                    text = "Agendar nueva cita",
                    onClick = onNewAppointment,
                    modifier = Modifier.widthIn(min = 180.dp)
                )
            }

            // Scroll solo en el contenido restante (texto + lista)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(24.dp)) // para empujar el texto un poco abajo

                Text(
                    text = "MIS CITAS",
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = quicksandFont,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFF5F5F5),
                            shape = RoundedCornerShape(25.dp)
                        )
                        .padding(vertical = 20.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (appointments.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp), // alto fijo para mensaje vacío
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay citas registradas",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.LightGray
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(appointments) { appointment ->
                            MyPetAppointmentCard(
                                appointment = appointment,
                                onClick = { onCardClick(appointment) }
                            )
                        }
                    }
                }
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
            onNewAppointment = { navController.navigate("registerAppointment") },
            onCardClick = { appointment ->
                navController.navigate("appointmentDetail/${appointment.id}")
            }
        )
    }
}
