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
import me.vitalpaw.ui.screens.veterinario.ToAssigned
import me.vitalpaw.ui.screens.veterinario.AppointmentScreen
import android.util.Log
import me.vitalpaw.viewmodels.SessionViewModel
import me.vitalpaw.ui.screens.veterinario.BienvenidoScreen
import me.vitalpaw.ui.screens.veterinario.PetAppointmentsScreen
import me.vitalpaw.ui.screens.veterinario.PetRecordDetailScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(navController: NavHostController, sessionViewModel: SessionViewModel) {
    AnimatedNavHost(navController = navController, startDestination = NavRoutes.Login.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        //Autenticacion
        composable(NavRoutes.Login.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) { LoginScreen(navController, sessionViewModel) }

        composable(NavRoutes.Register.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) { Register(navController) }

        //Inicio
        composable(NavRoutes.Bienvenido.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            BienvenidoScreen(navController, token, sessionViewModel)
        }


        //Citas

        composable(
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
            route = NavRoutes.Home.route,
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            AppointmentScreen(navController, token)
        }

        composable(route = NavRoutes.AppointmentDetail.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?:""
            Log.d("NavigationArgs", "TOKEN: $token | APPOINTMENT: $appointmentId") // <--- Agrega este Log
            AppointmentDetailScreen(navController = navController, appointmentId = appointmentId, token = token, sessionViewModel)
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

        //Historial medico
        composable(
            route = NavRoutes.PetAppointment.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
            arguments = listOf(
                navArgument("petId") { type = NavType.StringType },
                navArgument("token") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId") ?: ""
            val token = backStackEntry.arguments?.getString("token") ?: ""
            PetAppointmentsScreen(navController = navController, petId = petId, token = token)
        }


        composable(route = NavRoutes.MedicalRecordDetail.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId") ?: ""
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val medicalRecordId = backStackEntry.arguments?.getString("medicalRecordId") ?:""
            Log.d("NavigationArgs", " medical record: $medicalRecordId") // <--- Agrega este Log
            PetRecordDetailScreen(navController = navController, medicalRecordId = medicalRecordId, token = token, petId = petId)
        }
    }
}
