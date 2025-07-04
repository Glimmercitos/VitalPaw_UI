package me.vitalpaw.ui.screens.veterinario

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vitalpaw.ui.components.Vetcards.PetAppointmentCard
import me.vitalpaw.ui.components.buttons.SalirButton
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.MedicalRecordViewModel
import me.vitalpaw.viewmodels.SessionViewModel
import androidx.compose.runtime.getValue
import me.vitalpaw.ui.components.navigationBar.TopBarStatic

@Composable
fun PetAppointmentsScreen(
    navController: NavController,
    petId: String,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    viewModel: MedicalRecordViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        sessionViewModel.loadUserData()
    }

    val token by sessionViewModel.firebaseToken.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()


    LaunchedEffect(token, petId) {
        token?.let {
            viewModel.loadRecordsByPetId(it, petId)
        }
    }


    val medicalRecords by viewModel.recordsByPetId.collectAsState()
    val petName = medicalRecords.firstOrNull()?.pet?.name ?: "Mascota"

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                color = Color(0xFF6980BF))
        }
        return
    }

    Scaffold(
        topBar = {
            TopBarStatic(
                title = "HISTORIAL MEDICO",
                navController = navController,
                sessionViewModel = sessionViewModel
            )
        },
        containerColor = Color.White
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 27.dp, vertical = 18.dp)
                    .padding(bottom = 80.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Historial de citas de: $petName",
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = quicksandFont,
                    modifier = Modifier.padding(vertical = 10.dp),
                )

                if (medicalRecords.isEmpty()) {
                    Text("No hay citas anteriores para esta mascota.")
                } else {
                    medicalRecords.forEach { record ->
                        PetAppointmentCard(
                            record = record,
                            onDetailsClick = {
                                Log.d("PetRecord", "ID recibido: ${record.id}")
                                navController.navigate(
                                    NavRoutes.MedicalRecordDetail.createRoute(
                                        petId,
                                        record.id
                                    )
                                )
                            }
                        )
                    }
                }
            }

            Box(modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 27.dp, vertical = 12.dp)
                .padding(bottom = 7.dp),
                contentAlignment = Alignment.Center)
            {
                SalirButton { navController.popBackStack() }
            }
        }
    }
}
