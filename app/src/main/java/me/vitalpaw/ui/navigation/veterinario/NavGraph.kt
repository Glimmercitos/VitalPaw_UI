package me.vitalpaw.ui.navigation.veterinario

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import me.vitalpaw.ui.screens.LoginScreen
import me.vitalpaw.ui.screens.Register
import me.vitalpaw.ui.screens.veterinario.AppointmentDetailScreen
import me.vitalpaw.ui.screens.veterinario.ToAssigned
import me.vitalpaw.ui.screens.veterinario.AppointmentScreen
import me.vitalpaw.ui.screens.veterinario.BienvenidoScreen
import me.vitalpaw.ui.screens.veterinario.PetAppointmentsScreen
import me.vitalpaw.ui.screens.veterinario.PetRecordDetailScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(navController: NavHostController) {
    val defaultEnterTransition = fadeIn(animationSpec = tween(300))
    val defaultExitTransition = fadeOut(animationSpec = tween(300))

    AnimatedNavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route,
        enterTransition = { defaultEnterTransition },
        exitTransition = { defaultExitTransition }
    ) {
        composable(NavRoutes.Login.route) {
            LoginScreen(navController)
        }

        composable(NavRoutes.Register.route) {
            Register(navController)
        }

        composable(NavRoutes.Bienvenido.route) {
            BienvenidoScreen(navController)
        }

        composable(NavRoutes.ToAssigned.route) {
            ToAssigned(navController)
        }

        composable(NavRoutes.Home.route) {
            AppointmentScreen(navController)
        }

        composable(NavRoutes.PetAppointment.route) { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId") ?: ""
            PetAppointmentsScreen(navController = navController, petId = petId)
        }

        composable(NavRoutes.AppointmentDetail.route) { backStackEntry ->
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?: ""
            AppointmentDetailScreen(navController = navController, appointmentId = appointmentId)
        }
        composable(NavRoutes.PetAppointmentDetail.route) { backStackEntry ->
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?: ""
            PetRecordDetailScreen(navController = navController, appointmentId = appointmentId)
        }
    }
}