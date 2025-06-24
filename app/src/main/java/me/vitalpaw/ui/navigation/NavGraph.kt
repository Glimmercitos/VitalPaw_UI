package me.vitalpaw.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import me.vitalpaw.ui.screens.LoginScreen
import me.vitalpaw.ui.screens.Register
import me.vitalpaw.ui.screens.shop.CartProductDetailScreen
import me.vitalpaw.ui.screens.shop.CartScreen
import me.vitalpaw.ui.screens.shop.HomeShopScreen
import me.vitalpaw.ui.screens.shop.ProductDetailScreen
import me.vitalpaw.ui.screens.veterinario.AppointmentDetailScreen
import me.vitalpaw.ui.screens.veterinario.ToAssigned
import me.vitalpaw.ui.screens.veterinario.AppointmentScreen
import me.vitalpaw.viewmodels.CartViewModel
import me.vitalpaw.viewmodels.HomeShopViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = NavRoutes.Login.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        composable(NavRoutes.Login.route) {
            LoginScreen(navController)
        }

        composable(NavRoutes.Register.route) {
            Register(navController)
        }

        composable(NavRoutes.Home.route) {
            AppointmentScreen(navController)
        }

        composable(NavRoutes.ToAssigned.route) {
            ToAssigned(navController)
        }

        composable(NavRoutes.AppointmentDetail.route) { backStackEntry ->
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?: ""
            AppointmentDetailScreen(navController = navController, appointmentId = appointmentId)
        }

        composable(NavRoutes.HomeShopScreen.route) {
            val shopViewModel = hiltViewModel<HomeShopViewModel>()
            HomeShopScreen(
                navController = navController,
                viewModel = shopViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable("${NavRoutes.ProductDetail.route}/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: return@composable
            val shopViewModel = hiltViewModel<HomeShopViewModel>()
            val cartViewModel = hiltViewModel<CartViewModel>()
            ProductDetailScreen(
                navController = navController,
                shopViewModel = shopViewModel,
                cartViewModel = cartViewModel,
                productIndex = index,
                onBack = { navController.popBackStack() }
            )
        }
        composable("cartProductDetail/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: return@composable
            val cartViewModel = hiltViewModel<CartViewModel>()
            CartProductDetailScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                productIndex = index,
                onBack = { navController.popBackStack() }
            )
        }

    }
}
