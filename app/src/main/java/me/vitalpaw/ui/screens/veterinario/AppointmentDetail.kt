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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.text.style.TextAlign
import me.vitalpaw.ui.components.buttons.AsignarCitaButton
import me.vitalpaw.ui.theme.quicksandFont


@Composable
fun AppointmentDetailScreen(navController: NavController, appointmentId: String, viewModel: MedicalRecordViewModel = hiltViewModel()
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

    var errorTitle by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    var succesTitle by remember { mutableStateOf("") }
    var succesMessage by remember { mutableStateOf("") }


    fun handleSave() {
        try {
            if (recordValue.notes.isBlank() || recordValue.treatment.isBlank()) {
                errorTitle = "Error al asignar cita"
                errorMessage = "Campos vacios"
                throw IllegalArgumentException("campos vacios")
            }
            viewModel.saveRecord()
            succesTitle = "Cita guardada!"
            succesMessage = "Cita asignada correctamente"
            showSuccessDialog = true

        } catch (e: Exception) {
            if (errorTitle.isEmpty()){
                errorTitle = "Error al asignar cita"
                errorMessage = "Vuelve a intentar"
            }
            showErrorDialog = true
        }
    }

    val scrollState = rememberScrollState()

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
                .border(3.dp, Color(0xFF4AA5C8), CircleShape)
        )

        Spacer(Modifier.height(20.dp))
        DisabledText(pet.name)
        Spacer(Modifier.height(15.dp))

        Row(
            Modifier.fillMaxWidth()
            .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DisabledTextRow(pet.species, modifier = Modifier.weight(1f))
            DisabledTextRow("${pet.age} ${pet.unitAge}", modifier = Modifier.weight(1f))
            DisabledTextRow(if (pet.gender) "M" else "F", modifier = Modifier.weight(1f))
        }
        Spacer(Modifier.height(15.dp))

        DisabledText(pet.breed)
        Spacer(Modifier.height(15.dp))

        DisabledText("${pet.weight} Kg")
        Spacer(Modifier.height(15.dp))

        DisabledText(recordValue.service)
        Spacer(Modifier.height(20.dp))


        OutlinedTextField(
            value = recordValue.description,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            enabled = false,
            shape = RoundedCornerShape(20.dp),
            textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledContainerColor = Color.Transparent,
                disabledBorderColor = Color(0xFF4AA5C8)
            )
        )

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = recordValue.notes,
            onValueChange = { viewModel.updateNotes(it) },
            label = { Text("Notas", fontFamily = quicksandFont) },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            shape = RoundedCornerShape(20.dp),
            textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF4AA5C8),
                unfocusedBorderColor = Color(0xFF4AA5C8)
            )
        )


        Spacer(Modifier.height(20.dp))


        OutlinedTextField(
            value = recordValue.treatment,
            onValueChange = { viewModel.updateTreatment(it) },
            label = { Text("Tratamiento", fontFamily = quicksandFont) },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            shape = RoundedCornerShape(20.dp),
            textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF4AA5C8),
                unfocusedBorderColor = Color(0xFF4AA5C8)
            )
        )

        Spacer(modifier = Modifier.height(40.dp))

        AsignarCitaButton(onClick = {
            navController.navigate(NavRoutes.ToAssigned.route){
                popUpTo(NavRoutes.ToAssigned.route){inclusive = true}
            }
        })

        Spacer(modifier = Modifier.height(40.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
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
        onDismiss = { showSuccessDialog = false
            succesTitle = ""
            succesMessage = ""
            navController.navigate(NavRoutes.Home.route) {
                popUpTo(NavRoutes.Home.route) { inclusive = true }
            }
        },
        Title = succesTitle,
        Message = succesMessage
    )

    ErrorDialog(
        show = showErrorDialog,
        onDismiss = { showErrorDialog = false
        errorTitle = ""
        errorMessage = ""
        },
        title = errorTitle,
        message = errorMessage
    )
}

@Composable
fun DisabledText(value: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        enabled = false,
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(40.dp),
        textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont),
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = Color.Black,
            disabledContainerColor = Color.Transparent,
            disabledBorderColor = Color(0xFF4AA5C8)
        )
    )
}

@Composable
fun DisabledTextRow(value: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        enabled = false,
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(40.dp),
        textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont, textAlign = TextAlign.Center),
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = Color.Black,
            disabledContainerColor = Color.Transparent,
            disabledBorderColor = Color(0xFF4AA5C8)
        )
    )
}



