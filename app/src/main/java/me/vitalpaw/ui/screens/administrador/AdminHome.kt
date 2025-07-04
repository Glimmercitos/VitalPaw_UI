package me.vitalpaw.ui.screens.administrador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.vitalpaw.ui.components.navigationBar.HomeTopBar
import me.vitalpaw.ui.components.navigationBar.RoleBasedDrawerScaffold
import me.vitalpaw.ui.components.admin.QuickActionCard
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.viewmodels.SessionViewModel
import androidx.navigation.NavHostController

@Composable
fun AdminHomeScreen(
    navController: NavHostController,
    sessionViewModel: SessionViewModel = hiltViewModel()
) {
    RoleBasedDrawerScaffold(
        sessionViewModel = sessionViewModel,
        navController = navController
    ) { onMenuClick ->

        LaunchedEffect(Unit) {
            sessionViewModel.loadUserData()
        }

        Scaffold(
            topBar = {
                HomeTopBar(
                    title = "HOME",
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // agregar el paddingValues para scaffold
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                QuickActionCard(
                    icon = Icons.Default.Person,
                    label = "Veterinarios",
                    onClick = {
                        navController.navigate(NavRoutes.AllVets.route) {
                            popUpTo(NavRoutes.AllVets.route) { inclusive = true }
                        }
                    }
                )
                QuickActionCard(
                    icon = Icons.Default.Event,
                    label = "Citas",
                    onClick = {
                        navController.navigate(NavRoutes.AllAppointments.route) {
                            popUpTo(NavRoutes.AllAppointments.route) { inclusive = true }
                        }
                    }
                )
                QuickActionCard(
                    icon = Icons.Default.HealthAndSafety,
                    label = "Asignar rol",
                    onClick = {
                        navController.navigate(NavRoutes.AssignVetRol.route) {
                            popUpTo(NavRoutes.AssignVetRol.route) { inclusive = true }
                        }
                    }
                )
                QuickActionCard(
                    icon = Icons.Default.AttachMoney,
                    label = "Recargas",
                    onClick = {
                        navController.navigate(NavRoutes.RechargeVitalCoins.route) {
                            popUpTo(NavRoutes.RechargeVitalCoins.route) { inclusive = true }
                        }
                    }
                )
                QuickActionCard(
                    icon = Icons.Default.BarChart,
                    label = "Ganancias",
                    onClick = {
                        navController.navigate(NavRoutes.RedeemedCoins.route) {
                            popUpTo(NavRoutes.RedeemedCoins.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
