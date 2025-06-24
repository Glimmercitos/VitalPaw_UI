package me.vitalpaw.ui.screens.veterinario

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vitalpaw.ui.components.buttons.SalirButton
import me.vitalpaw.viewmodels.MedicalRecordViewModel
import me.vitalpaw.ui.theme.quicksandFont

@Composable
fun PetRecordDetailScreen(
    navController: NavController,
    appointmentId: String,
    viewModel: MedicalRecordViewModel = hiltViewModel()
) {
    LaunchedEffect(appointmentId) {
        viewModel.loadRecordById(appointmentId)
    }

    val record by viewModel.record.collectAsState()
    val scrollState = rememberScrollState()

    if (record == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No se encontró información médica para esta cita.",
                fontFamily = quicksandFont,
                color = Color.Gray
            )
        }
    } else {
        val pet = record!!.pet

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

            Spacer(modifier = Modifier.height(30.dp))

            DisabledText(value = record!!.service, label = "Tipo de servicio")

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = record!!.description,
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

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = record!!.notes,
                onValueChange = {},
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                label = {
                    Text("Notas", fontFamily = quicksandFont, color = Color(0xFFAAAAAA))
                },
                shape = RoundedCornerShape(20.dp),
                textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.Black,
                    disabledContainerColor = Color.Transparent,
                    disabledBorderColor = Color(0xFF6E7AE6)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = record!!.treatment,
                onValueChange = {},
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                label = {
                    Text("Tratamiento", fontFamily = quicksandFont, color = Color(0xFFAAAAAA))
                },
                shape = RoundedCornerShape(20.dp),
                textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.Black,
                    disabledContainerColor = Color.Transparent,
                    disabledBorderColor = Color(0xFF6E7AE6)
                )
            )

            Spacer(modifier = Modifier.height(60.dp))

            SalirButton { navController.popBackStack() }

        }
    }
}


