package me.vitalpaw.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.vitalpaw.ui.navigation.NavRoutes.Register
import me.vitalpaw.ui.navigation.NavRoutes.ToAssigned
import me.vitalpaw.ui.screens.LoginScreen
//import me.vitalpaw.ui.screens.LoginScreen
import me.vitalpaw.ui.screens.Register
import me.vitalpaw.ui.screens.veterinario.AppointmentDetailScreen
import me.vitalpaw.ui.screens.veterinario.ToAssigned
import me.vitalpaw.ui.screens.veterinario.AppointmentScreen
//import me.vitalpaw.ui.screens.veterinario.*
//import me.vitalpaw.ui.screens.cliente.HomeScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavRoutes.Home.route) {
        composable(NavRoutes.Login.route) { LoginScreen(navController) }
        composable(NavRoutes.Register.route) { Register(navController) }
        composable(NavRoutes.Home.route) { AppointmentScreen(navController) }
        composable(NavRoutes.ToAssigned.route) { ToAssigned(navController) }
        composable(route = NavRoutes.AppointmentDetail.route) {
            backStackEntry ->
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?:""
            AppointmentDetailScreen(navController = navController, appointmentId = appointmentId)
        }

    }
}

