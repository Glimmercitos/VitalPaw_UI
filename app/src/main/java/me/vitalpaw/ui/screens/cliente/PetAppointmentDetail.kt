package me.vitalpaw.ui.screens.cliente

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vitalpaw.R
import me.vitalpaw.ui.components.topbar.TopBar
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.cliente.MyAppointmentsPetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetAppointmentDetail(
    navController: NavController,
    appointmentId: String?,
    viewModel: MyAppointmentsPetViewModel = hiltViewModel()
) {
    val appointments = viewModel.appointments.collectAsState().value
    val appointment = appointments.find { it.id == appointmentId }

    if (appointment == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Cita no encontrada",
                fontFamily = quicksandFont,
                fontSize = 18.sp,
                color = Color.Red
            )
        }
        return
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            TopBar(title = "DETALLE DE CITA") {
                navController.popBackStack()
            }

            Spacer(modifier = Modifier.height(5.dp))

            // Foto centrada de la mascota
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = appointment.pet.imageRes),
                    contentDescription = "Foto de la mascota",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Campos
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                InfoField(label = "Nombre mascota", value = appointment.pet.name)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        InfoField(label = "Especie", value = appointment.pet.species)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        InfoField(
                            label = "Edad",
                            value = "${appointment.pet.age} ${appointment.pet.unitAge ?: "años"}"
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        InfoField(label = "Género", value = if (appointment.pet.gender) "M" else "F")
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Información de la cita",
                    fontFamily = quicksandFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF00695C),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                InfoField(label = "Servicio", value = appointment.service)
                InfoField(label = "Fecha", value = appointment.date)
                InfoField(label = "Hora", value = appointment.time)
                InfoField(label = "Descripción", value = appointment.description)

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}


@Composable
fun InfoField(label: String, value: String) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontFamily = quicksandFont,
            color = Color.Gray,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFF90CAF9),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Text(
                text = value,
                fontSize = 16.sp,
                fontFamily = quicksandFont,
                color = Color.DarkGray
            )
        }
    }
}
