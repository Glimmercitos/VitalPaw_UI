package me.vitalpaw.ui.screens.veterinario

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.vitalpaw.viewmodels.veterinario.MedicalRecordViewModel
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import me.vitalpaw.ui.components.buttons.GuardarCitaButton
import me.vitalpaw.ui.components.buttons.CancelarCitaButton
import me.vitalpaw.ui.components.modal.ConfirmationDialog
import me.vitalpaw.ui.components.modal.ErrorDialog
import me.vitalpaw.ui.navigation.NavRoutes
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.text.style.TextAlign
import me.vitalpaw.ui.components.buttons.AsignarCitaButton
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.veterinario.AppointmentViewModel


@Composable
fun AppointmentDetailScreen(
    navController: NavController,
    appointmentId: String,
    appointmentViewModel: AppointmentViewModel = hiltViewModel(),
    medicalRecordViewModel: MedicalRecordViewModel = hiltViewModel()
) {
    LaunchedEffect(appointmentId) {
        val appointment = appointmentViewModel.getAppointmentById(appointmentId)
        medicalRecordViewModel.createNewRecordFromAppointment(appointment)}


    val record by medicalRecordViewModel.record.collectAsState()
    val showSuccessDialog by medicalRecordViewModel.showSuccessDialog.collectAsState()
    val showErrorDialog by medicalRecordViewModel.showErrorDialog.collectAsState()
    val successTitle by medicalRecordViewModel.successTitle.collectAsState()
    val successMessage by medicalRecordViewModel.successMessage.collectAsState()
    val errorTitle by medicalRecordViewModel.errorTitle.collectAsState()
    val errorMessage by medicalRecordViewModel.errorMessage.collectAsState()

    val scrollState = rememberScrollState()

    record?.let { recordValue ->
        val pet = recordValue.pet

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 30.dp, vertical = 18.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = pet.imageRes),
                contentDescription = pet.name,
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .border(3.dp, Color(0xFF6E7AE6), CircleShape)
            )

            Spacer(Modifier.height(20.dp))
            DisabledText(pet.name, "Nombre de la mascota")
            Spacer(Modifier.height(15.dp))

            Row(
                Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DisabledTextRow(value = pet.species, label = "especie", modifier = Modifier.weight(1f))
                DisabledTextRow(value = "${pet.age} ${pet.unitAge}", label = "edad", modifier = Modifier.weight(1f))
                DisabledTextRow(if (pet.gender) "M" else "F", "genero", modifier = Modifier.weight(1f))
            }

            Spacer(Modifier.height(15.dp))

            DisabledText(value = pet.breed, label = "raza")

            Spacer(Modifier.height(15.dp))

            DisabledText(value = "${pet.weight} Kg", label = "peso")

            Spacer(Modifier.height(15.dp))

            DisabledText(value = recordValue.service, label = "Tipo de servicio")

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = recordValue.description,
                onValueChange = {},
                enabled = false,
                modifier = Modifier.fillMaxWidth().height(100.dp),
                label = { Text("Descripcion", fontFamily = quicksandFont, color = Color(0xFFAAAAAA)) },
                shape = RoundedCornerShape(20.dp),
                textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.Black,
                    disabledContainerColor = Color.Transparent,
                    disabledBorderColor = Color(0xFF6E7AE6)
                )
            )

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = recordValue.notes,
                onValueChange = { medicalRecordViewModel.updateNotes(it) },
                label = { Text("Notas", fontFamily = quicksandFont, color = Color(0xFFAAAAAA)) },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                shape = RoundedCornerShape(20.dp),
                textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6E7AE6),
                    unfocusedBorderColor = Color(0xFF6E7AE6)
                )
            )

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = recordValue.treatment,
                onValueChange = { medicalRecordViewModel.updateTreatment(it) },
                label = { Text("Tratamiento", fontFamily = quicksandFont, color = Color(0xFFAAAAAA)) },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                shape = RoundedCornerShape(20.dp),
                textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6E7AE6),
                    unfocusedBorderColor = Color(0xFF6E7AE6)
                )
            )

            Spacer(Modifier.height(40.dp))

            AsignarCitaButton(onClick = {
                navController.navigate(NavRoutes.ToAssigned.route) {
                    popUpTo(NavRoutes.ToAssigned.route) { inclusive = true }
                }
            })

            Spacer(Modifier.height(40.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CancelarCitaButton {
                    navController.navigate(NavRoutes.Home.route) {
                        popUpTo(NavRoutes.Home.route) { inclusive = true }
                    }
                }

                GuardarCitaButton(onClick = { medicalRecordViewModel.saveRecord() })
            }
        }

        ConfirmationDialog(
            show = showSuccessDialog,
            onDismiss = {
                medicalRecordViewModel.dismissSuccessDialog()
                navController.navigate(NavRoutes.Home.route) {
                    popUpTo(NavRoutes.Home.route) { inclusive = true }
                }
            },
            Title = successTitle,
            Message = successMessage
        )

        ErrorDialog(
            show = showErrorDialog,
            onDismiss = { medicalRecordViewModel.dismissErrorDialog() },
            title = errorTitle,
            message = errorMessage
        )
    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Cargando...")
    }
}


@Composable
fun DisabledText(value: String, label: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        enabled = false,
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        label = { Text(label, fontFamily = quicksandFont, color = Color(0xFFAAAAAA)) },
        shape = RoundedCornerShape(40.dp),
        textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont),
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = Color.Black,
            disabledContainerColor = Color.Transparent,
            disabledBorderColor = Color(0xFF6E7AE6)
        )
    )
}

@Composable
fun DisabledTextRow(value: String, label: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        enabled = false,
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        label = { Text(label, fontFamily = quicksandFont, color = Color(0xFFAAAAAA)) },
        shape = RoundedCornerShape(40.dp),
        textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont, textAlign = TextAlign.Center),
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = Color.Black,
            disabledContainerColor = Color.Transparent,
            disabledBorderColor = Color(0xFF6E7AE6)
        )
    )
}



