package me.vitalpaw.ui.screens.veterinario

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import me.vitalpaw.R
import me.vitalpaw.ui.components.buttons.SalirButton
import me.vitalpaw.ui.components.navigationBar.TopBarStatic
import me.vitalpaw.viewmodels.MedicalRecordViewModel
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.SessionViewModel

@Composable
fun PetRecordDetailScreen(
    navController: NavController,
    petId: String,
    medicalRecordId: String,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    viewModel: MedicalRecordViewModel = hiltViewModel()
) {
    val token by sessionViewModel.firebaseToken.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(medicalRecordId, petId) {
        token?.let {
            viewModel.loadMedicalRecord(it, medicalRecordId, petId)
        }
    }

    val record by viewModel.medicalRecord.collectAsState()
    val scrollState = rememberScrollState()

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color(0xFF3695B9))
        }
        return
    }

    if (record == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No se encontró información médica para esta cita.",
                fontFamily = quicksandFont,
                color = Color.Gray
            )
        }
    } else {
        val safeRecord = record ?: return
        val pet = safeRecord.pet

        Scaffold(
            topBar = {
                TopBarStatic(
                    title = "DETALLE DE HISTORIAL",
                    navController = navController,
                    sessionViewModel = sessionViewModel
                )
            },
            containerColor = Color.White
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 30.dp, vertical = 18.dp)
                    .verticalScroll(scrollState)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = pet.imageUrl.takeIf { it.isNotBlank() } ?: R.drawable.petphoto,
                    contentDescription = pet.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .border(3.dp, Color(0xFF3695B9), CircleShape)
                )

                Spacer(modifier = Modifier.height(30.dp))

                DisabledText(value = safeRecord.service ?: "servicio", label = "Tipo de servicio")

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = safeRecord.description ?: "descripcion",
                    onValueChange = {},
                    enabled = false,
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    label = {
                        Text(
                            "Descripcion",
                            fontFamily = quicksandFont,
                            color = Color(0xFFAAAAAA)
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Black,
                        disabledContainerColor = Color.Transparent,
                        disabledBorderColor = Color(0xFF3695B9)
                    )
                )

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = safeRecord.notes ?: "notas",
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
                        disabledBorderColor = Color(0xFF3695B9)
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = safeRecord.treatment ?: "tratamiento",
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
                        disabledBorderColor = Color(0xFF3695B9)
                    )
                )

                Spacer(modifier = Modifier.height(60.dp))

                SalirButton { navController.popBackStack() }

            }
        }
    }

}
