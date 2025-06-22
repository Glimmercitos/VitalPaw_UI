package me.vitalpaw.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.vitalpaw.ui.screens.shop.CartScreen
import me.vitalpaw.ui.screens.shop.HomeShopScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HomeShopScreen.route,
        modifier = modifier
    ) {
        composable(NavRoutes.HomeShopScreen.route) {
            HomeShopScreen(
                navController = navController,
                onBack = { navController.popBackStack() }
            )
        }
        composable(NavRoutes.Cart.route) {
            CartScreen()
        }

        // Aquí puedes agregar más pantallas si necesitas:
        // composable(NavRoutes.Login.route) { ... }
        // composable(NavRoutes.ToAssigned.route) { ... }
    }
}
