package me.vitalpaw.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import me.vitalpaw.ui.screens.LoginScreen
import me.vitalpaw.ui.screens.Register
import me.vitalpaw.ui.screens.veterinario.AppointmentDetailScreen
//import me.vitalpaw.ui.screens.veterinario.AppointmentDetailScreen
import me.vitalpaw.ui.screens.veterinario.ToAssigned
import me.vitalpaw.ui.screens.veterinario.AppointmentScreen
<<<<<<< HEAD
import android.util.Log
import me.vitalpaw.viewmodels.SessionViewModel


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(navController: NavHostController, sessionViewModel: SessionViewModel) {
    AnimatedNavHost(navController = navController, startDestination = NavRoutes.Login.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        composable(NavRoutes.Login.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) { LoginScreen(navController, sessionViewModel) }
=======
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
>>>>>>> 908a8562511bdeebcd495f3bfdab885ea0e4d8c7

        composable(NavRoutes.Register.route) {
            Register(navController)
        }

<<<<<<< HEAD
        composable(
            route = NavRoutes.Home.route,
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            AppointmentScreen(navController, token)
        }


        composable(
            route = NavRoutes.ToAssigned.fullRoute,
            arguments = listOf(
                navArgument("appointmentId") { type = NavType.StringType },
                navArgument("token") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?: ""
            val token = backStackEntry.arguments?.getString("token") ?: ""
            Log.d("NavArgs", "ToAssigned -> appointmentId=$appointmentId, token=$token")
            ToAssigned(navController, appointmentId)
        }


        composable(route = NavRoutes.AppointmentDetail.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?:""
            Log.d("NavigationArgs", "TOKEN: $token | APPOINTMENT: $appointmentId") // <--- Agrega este Log
            AppointmentDetailScreen(navController = navController, appointmentId = appointmentId, token = token, sessionViewModel)
=======
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
>>>>>>> 908a8562511bdeebcd495f3bfdab885ea0e4d8c7
        }
        composable(NavRoutes.PetAppointmentDetail.route) { backStackEntry ->
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?: ""
            PetRecordDetailScreen(navController = navController, appointmentId = appointmentId)
        }
    }
}