package me.vitalpaw.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
import me.vitalpaw.ui.screens.cliente.MyPetDetail
import me.vitalpaw.ui.screens.cliente.PetAppointmentDetail
import me.vitalpaw.ui.screens.cliente.RegisterPetScreen
import me.vitalpaw.ui.screens.shop.CartProductDetailScreen
import me.vitalpaw.ui.screens.shop.HomeShopScreen
import me.vitalpaw.ui.screens.shop.ProductDetailScreen
import me.vitalpaw.ui.screens.shop.ShopDetailScreen
import me.vitalpaw.viewmodels.shop.CartViewModel
import me.vitalpaw.viewmodels.shop.HomeShopViewModel


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
        startDestination = NavRoutes.HomeClient.route,
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

        //Cliente
        composable(NavRoutes.Shop.route) {
            ShopScreen(navController)
        }

        composable(NavRoutes.MyPetAssigned.route) {
            MyPetAssignedScreen(navController = navController)
        }

        composable(NavRoutes.RegisterAppointment.route) {
            RegisterAppointment(navController)
        }
        composable(NavRoutes.Home.route) {
            HomeScreen(navController)
        }

        composable(NavRoutes.RegisterPet.route) {
            RegisterPetScreen(navController)
        }

        composable(NavRoutes.MyPetAppointment.route) {
            MyPetAppointmentScreen(navController = navController)
        }
        //Detalle MIS CITAS
        composable(
            route = "pet_appointment_detail/{appointmentId}",
            arguments = listOf(navArgument("appointmentId") { type = NavType.StringType })
        ) { backStackEntry ->
            val appointmentId = backStackEntry.arguments?.getString("appointmentId")
            PetAppointmentDetail(
                navController = navController,
                appointmentId = appointmentId
            )
        }
        //Detalle MIS MASCOTAS
        composable(
            route = NavRoutes.MyPetDetail.route,
            arguments = listOf(navArgument("petId") { type = NavType.StringType })
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId")
            MyPetDetail(navController = navController, petId = petId)
        }


        composable("home_shop",
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            HomeShopScreen(
                navController = navController,
                onBack = { navController.popBackStack() }
            )
        }
        composable("shop") {
            ShopScreen(navController = navController)
        }
        composable(
            route = NavRoutes.ProductDetail.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
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
        composable(
            route = NavRoutes.ProductDetail.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0

            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavRoutes.HomeShop.route)
            }

            val shopViewModel: HomeShopViewModel = hiltViewModel()
            val cartViewModel: CartViewModel = hiltViewModel(parentEntry)

            ProductDetailScreen(
                navController = navController,
                shopViewModel = shopViewModel,
                cartViewModel = cartViewModel,
                productIndex = index,
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = NavRoutes.CartProductDetail.route,
            enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) }
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavRoutes.HomeShop.route) // 👈
            }

            val cartViewModel: CartViewModel = hiltViewModel(parentEntry)

            CartProductDetailScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(NavRoutes.CartRedeemDetail.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavRoutes.HomeShop.route)
            }
            val cartViewModel: CartViewModel = hiltViewModel(parentEntry)
            ShopDetailScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                onBack = { navController.popBackStack() },
                onCancel = { navController.popBackStack() },
                onConfirm = {
                    navController.navigate(NavRoutes.HomeShop.route) {
                        popUpTo(NavRoutes.CartProductDetail.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
