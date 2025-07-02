package me.vitalpaw.ui.navigation.administrador

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import com.google.accompanist.navigation.animation.AnimatedNavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.screens.AssignVeterinarianScreen
import me.vitalpaw.ui.screens.LoginScreen
import me.vitalpaw.ui.screens.Register
import me.vitalpaw.ui.screens.administrador.AdminHomeScreen
import me.vitalpaw.ui.screens.administrador.AllAppointmentsScreen
import me.vitalpaw.ui.screens.administrador.AllVeterinarianScreen
import me.vitalpaw.ui.screens.administrador.RechargeVitalCoinsScreen
import me.vitalpaw.ui.screens.administrador.RedeemedCoinsScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AdminNavGraph(navController: NavHostController){
    val defaultEnterTransition = fadeIn(animationSpec = tween(300))
    val defaultExitTransition = fadeOut(animationSpec = tween(300))

    AnimatedNavHost(
        navController = navController,
        startDestination = NavRoutes.AdminHome.route,
        enterTransition = { defaultEnterTransition },
        exitTransition = { defaultExitTransition }
    ){
        composable(NavRoutes.Login.route)
        { LoginScreen(navController) }

        composable(NavRoutes.Register.route)
        { Register(navController) }

        composable(NavRoutes.AllAppointments.route)
        { AllAppointmentsScreen(navController) }

        composable(NavRoutes.AllVets.route) {
            AllVeterinarianScreen(navController = navController)
        }

        composable(
            route = NavRoutes.AssignVetRol.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            AssignVeterinarianScreen(navController = navController, userId = userId)
        }

        composable(NavRoutes.RechargeVitalCoins.route){
            RechargeVitalCoinsScreen(navController = navController)
        }

        composable(NavRoutes.RedeemedCoins.route){
            RedeemedCoinsScreen(navController = navController)
        }

        composable(NavRoutes.AdminHome.route){
            AdminHomeScreen(navController = navController)
        }
    }
}