package me.vitalpaw.ui.screens.administrador

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.vitalpaw.models.User
import me.vitalpaw.ui.components.admin.VeterinarianCard
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.admin.VeterinarianViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import me.vitalpaw.ui.navigation.veterinario.NavRoutes

@Composable
fun AllVeterinarianScreen(
    viewModel: VeterinarianViewModel = hiltViewModel(),
    navController: NavController,
) {
    LaunchedEffect(Unit) {
        viewModel.loadVeterinarians()
    }
    val veterinarians by viewModel.veterinarians

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 4.dp),
    ) {
        if (veterinarians.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
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
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                veterinarians.forEach { user ->
                    VeterinarianCard(
                        user = user,
                        onEditClick = { navController.navigate(NavRoutes.AssignVetRol.createRoute(user.id!!))}
                    )
                }
            }
        }
    }
}
