package me.vitalpaw.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import me.vitalpaw.ui.screens.LoginScreen
import me.vitalpaw.ui.screens.Register
import me.vitalpaw.ui.screens.shop.CartProductDetailScreen
import me.vitalpaw.ui.screens.shop.CartScreen
import me.vitalpaw.ui.screens.shop.HomeShopScreen
import me.vitalpaw.ui.screens.shop.ProductDetailScreen
import me.vitalpaw.ui.screens.shop.ShopDetailScreen
import me.vitalpaw.ui.screens.shop.ShopScreen
import me.vitalpaw.ui.screens.veterinario.AppointmentDetailScreen
import me.vitalpaw.ui.screens.veterinario.ToAssigned
import me.vitalpaw.ui.screens.veterinario.AppointmentScreen
import me.vitalpaw.viewmodels.shop.CartViewModel
import me.vitalpaw.viewmodels.shop.HomeShopViewModel

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

        composable(NavRoutes.ShopSplash.route) {
            ShopScreen { // muestra solo 1 segundo
                navController.navigate(NavRoutes.HomeShop.route) {
                    popUpTo(NavRoutes.ShopSplash.route) { inclusive = true }
                }
            }
        }

        composable(NavRoutes.HomeShop.route) {
            HomeShopScreen(
                navController = navController,
                onBack = { navController.popBackStack() }
            )
        }

        composable(NavRoutes.ProductDetail.route) { backStackEntry ->
            val index = backStackEntry.arguments?.getString("productIndex")?.toIntOrNull() ?: 0
            val shopViewModel: HomeShopViewModel = hiltViewModel()
            val cartViewModel: CartViewModel = hiltViewModel()
            ProductDetailScreen(
                navController = navController,
                shopViewModel = shopViewModel,
                cartViewModel = cartViewModel,
                productIndex = index,
                onBack = { navController.popBackStack() }
            )
        }

        composable(NavRoutes.Cart.route) {
            val cartViewModel: CartViewModel = hiltViewModel()
            CartProductDetailScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(NavRoutes.ShopDetail.route) {
            val cartViewModel: CartViewModel = hiltViewModel()
            ShopDetailScreen(
                navController = navController,
                onCancel = { navController.popBackStack() },
                onConfirm = {
                    // lógica de confirmación
                },
                cartViewModel = cartViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
