package me.vitalpaw.ui.navigation

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
import me.vitalpaw.ui.screens.cliente.HomeScreen
import me.vitalpaw.ui.screens.cliente.MyPetAssignedScreen
import me.vitalpaw.ui.screens.cliente.RegisterAppointment
import me.vitalpaw.ui.screens.shop.ShopScreen
import me.vitalpaw.ui.screens.veterinario.AppointmentDetailScreen
import me.vitalpaw.ui.screens.veterinario.ToAssigned
import me.vitalpaw.ui.screens.cliente.MyPetAppointmentScreen


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = NavRoutes.Login.route
)
{
    AnimatedNavHost(
        navController = navController,
        //startDestination = NavRoutes.Login.route,
        startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        composable(NavRoutes.Login.route) {
            LoginScreen(navController)
        }

        composable(NavRoutes.Register.route) {
            Register(navController)
        }

        composable(NavRoutes.HomeClient.route) {
            HomeScreen(navController = navController)
        }

        composable(NavRoutes.ToAssigned.route) {
            ToAssigned(navController)
        }

        composable(NavRoutes.AppointmentDetail.route) { backStackEntry ->
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?: ""
            AppointmentDetailScreen(navController = navController, appointmentId = appointmentId)
        }

        composable(NavRoutes.Shop.route) {
            ShopScreen(navController)
        }

        composable(NavRoutes.MyPetAssigned.route) {
            MyPetAssignedScreen(navController = navController)
        }

        composable(NavRoutes.RegisterAppointment.route) {
            RegisterAppointment(navController)
        }

        composable(NavRoutes.MyPetAppointment.route) {
            MyPetAppointmentScreen(navController = navController)
        }

        // Añade más rutas si necesitas ShopDetail, ProductDetail, Cart, etc.
    }
}
