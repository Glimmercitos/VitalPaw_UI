package me.vitalpaw.ui.screens.administrador

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.vitalpaw.ui.components.admin.VeterinarianCard
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.admin.VeterinarianViewModel
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import me.vitalpaw.ui.components.buttons.SalirButton
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.viewmodels.SessionViewModel

@Composable
fun AllVeterinarianScreen(
    navController: NavController,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    viewModel: VeterinarianViewModel = hiltViewModel()
) {
    val token by sessionViewModel.firebaseToken.collectAsState()
    LaunchedEffect(token) {
        token?.let {
            viewModel.loadVeterinarians(it)
        }
    }
    val veterinarians by viewModel.veterinarians

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 27.dp, vertical = 18.dp)
    ) {
        if (veterinarians.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay veterinarios registrados",
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = quicksandFont,
                    color = Color.Gray
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                veterinarians.forEach { user ->
                    VeterinarianCard(
                        user = user,
                        onEditClick = {
                            navController.navigate(NavRoutes.AssignVetRol.createRoute(user.id!!))
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            SalirButton {
                navController.navigate(NavRoutes.AdminHome.route) {
                    popUpTo(NavRoutes.AllVets.route) { inclusive = true }
                }
            }
        }
    }
}
