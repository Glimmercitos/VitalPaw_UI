package me.vitalpaw.ui.screens.veterinario

import androidx.compose.foundation.Image
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
import me.vitalpaw.viewmodels.MedicalRecordViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import me.vitalpaw.ui.components.buttons.GuardarCitaButton
import me.vitalpaw.ui.components.buttons.CancelarCitaButton
import me.vitalpaw.ui.components.modal.ConfirmationDialog
import me.vitalpaw.ui.components.modal.ErrorDialog
import me.vitalpaw.ui.navigation.NavRoutes
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextFieldDefaults


@Composable
fun AppointmentDetailScreen(
    // mismos parámetros
    navController: NavController,
    appointmentId: String,
    viewModel: MedicalRecordViewModel = hiltViewModel()
) {
    LaunchedEffect(appointmentId) {
        viewModel.loadRecordById(appointmentId)
    }

    val record by viewModel.record.collectAsState()
    val recordValue = record ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Cargando...")
        }
        return
    }
    val pet = recordValue.pet
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    fun handleSave() {
        try {
            if (recordValue.notes.isBlank() || recordValue.treatment.isBlank()) {
                throw IllegalArgumentException("Campos vacíos")
            }
            viewModel.saveRecord()
            showSuccessDialog = true

        } catch (e: Exception) {
            showErrorDialog = true
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = pet.imageRes),
            contentDescription = pet.name,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(3.dp, Color(0xFF3B82F6), CircleShape) // azul más parecido
        )

        Spacer(Modifier.height(16.dp))
        DisabledText(pet.name)

        Row(
            Modifier.fillMaxWidth()
            .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DisabledText(pet.species, modifier = Modifier.weight(1f))
            DisabledText("${pet.age} ${pet.unitAge}", modifier = Modifier.weight(1f))
            DisabledText(if (pet.gender) "M" else "F", modifier = Modifier.weight(1f))
        }

        DisabledText(pet.breed)
        DisabledText("${pet.weight} Kg")
        DisabledText(recordValue.service)

        OutlinedTextField(
            value = recordValue.description,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledContainerColor = Color.Transparent,
                disabledBorderColor = Color(0xFF3B82F6)
            )

        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = recordValue.notes,
            onValueChange = { viewModel.updateNotes(it) },
            label = { Text("Notas") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF3B82F6),
                unfocusedBorderColor = Color(0xFF3B82F6)
            )
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = recordValue.treatment,
            onValueChange = { viewModel.updateTreatment(it) },
            label = { Text("Tratamiento") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF3B82F6),
                unfocusedBorderColor = Color(0xFF3B82F6)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CancelarCitaButton(onClick = {
                navController.navigate(NavRoutes.Home.route) {
                    popUpTo(NavRoutes.Home.route) { inclusive = true }
                }
            })

            GuardarCitaButton(onClick = { handleSave() })
        }
    }

    ConfirmationDialog(
        show = showSuccessDialog,
        onDismiss = {
            showSuccessDialog = false
            navController.navigate(NavRoutes.Home.route) {
                popUpTo(NavRoutes.Home.route) { inclusive = true }
            }
        }
    )

    ErrorDialog(
        show = showErrorDialog,
        onDismiss = { showErrorDialog = false }
    )
}

@Composable
fun DisabledText(value: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        enabled = false,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = Color.Black,
            disabledContainerColor = Color.Transparent,
            disabledBorderColor = Color(0xFF3B82F6)
        )

    )
}


