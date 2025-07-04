package me.vitalpaw.ui.screens.administrador

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.vitalpaw.ui.components.admin.AdminAppoinmentCard
import me.vitalpaw.ui.components.navigationBar.HomeTopBar
import me.vitalpaw.ui.components.navigationBar.RoleBasedDrawerScaffold
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.viewmodels.SessionViewModel
import androidx.compose.foundation.lazy.items

import me.vitalpaw.viewmodels.veterinario.AppointmentViewModel

@Composable
fun AllAppointmentsScreen(
    navController: NavHostController,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    viewModel: AppointmentViewModel = hiltViewModel()){

    val token by sessionViewModel.firebaseToken.collectAsState()
    val appointments by viewModel.allAppointments.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(token) {
        token?.let {
            Log.d("AppointmentScreen", "Loading appointments with token: $it")
            viewModel.loadAllAppointments(it)
        }
    }
    RoleBasedDrawerScaffold(
        sessionViewModel = sessionViewModel,
        navController = navController
    ) { onMenuClick ->

        Scaffold(
            topBar = {
                HomeTopBar(
                    title = "CITAS",
                    onMenuClick = onMenuClick,
                    onHomeClick = {
                        navController.navigate(NavRoutes.AdminHome.route) {
                            popUpTo(NavRoutes.Login.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                )
            },
            containerColor = Color.White
        ) { paddingValues ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(start = 20.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    appointments.isEmpty() -> {
                        Text(
                            text = "Sin citas asignadas",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    else -> {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(15.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(appointments) { appointment ->
                                AdminAppoinmentCard(appointment = appointment)
                        }
                    }
                }
            }
        }
    }
    }
    
}




