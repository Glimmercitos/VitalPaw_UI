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
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.screens.AssignVeterinarianScreen
import me.vitalpaw.ui.screens.administrador.AdminHomeScreen
import me.vitalpaw.ui.screens.administrador.AllAppointmentsScreen
import me.vitalpaw.ui.screens.administrador.AllVeterinarianScreen
import me.vitalpaw.ui.screens.administrador.RechargeVitalCoinsScreen
import me.vitalpaw.ui.screens.administrador.RedeemedCoinsScreen
import me.vitalpaw.ui.screens.cliente.HomeScreen
import me.vitalpaw.ui.screens.cliente.MyPetAppointmentScreen
import me.vitalpaw.ui.screens.cliente.MyPetAssignedScreen
import me.vitalpaw.ui.screens.cliente.MyPetDetail
//import me.vitalpaw.ui.screens.cliente.MyPetAppointmentScreen
//import me.vitalpaw.ui.screens.cliente.MyPetAssignedScreen
import me.vitalpaw.ui.screens.cliente.RegisterAppointment
import me.vitalpaw.ui.screens.cliente.RegisterPetScreen
import me.vitalpaw.ui.screens.shop.CartProductDetailScreen
import me.vitalpaw.ui.screens.shop.HomeShopScreen
import me.vitalpaw.ui.screens.shop.ProductDetailScreen
import me.vitalpaw.ui.screens.shop.ShopDetailScreen
import me.vitalpaw.ui.screens.shop.ShopScreen
import me.vitalpaw.viewmodels.SessionViewModel
import me.vitalpaw.ui.screens.veterinario.BienvenidoScreen
import me.vitalpaw.ui.screens.veterinario.PetAppointmentsScreen
import me.vitalpaw.ui.screens.veterinario.PetRecordDetailScreen
import me.vitalpaw.viewmodels.MedicalRecordViewModel
import me.vitalpaw.viewmodels.shop.CartViewModel
import me.vitalpaw.viewmodels.shop.HomeShopViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(navController: NavHostController, sessionViewModel: SessionViewModel) {
    val defaultEnterTransition = fadeIn(animationSpec = tween(300))
    val defaultExitTransition = fadeOut(animationSpec = tween(300))
    AnimatedNavHost(navController = navController,
        startDestination = NavRoutes.Login.route,
        enterTransition = { defaultEnterTransition },
        exitTransition = { defaultExitTransition }
    ) {
        //Autenticacion
        composable(NavRoutes.Login.route,
        ) { LoginScreen(navController, sessionViewModel) }

        composable(NavRoutes.Register.route,
        ) { Register(navController) }

        //Inicio Veterinario
        composable(NavRoutes.Bienvenido.route,
        ) { BienvenidoScreen(navController, sessionViewModel) }


        //Citas
        composable(
            route = NavRoutes.Home.route,
        ) { AppointmentScreen(navController, sessionViewModel) }

        composable(route = NavRoutes.AppointmentDetail.route
        ) { backStackEntry ->
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?:""
            Log.d("NavigationArgs", " APPOINTMENT: $appointmentId")
            AppointmentDetailScreen(navController = navController, appointmentId = appointmentId, sessionViewModel)
        }

        composable(
            route = NavRoutes.ToAssigned.fullRoute,
            arguments = listOf(
                navArgument("appointmentId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?: ""
            Log.d("NavArgs", "ToAssigned -> appointmentId=$appointmentId")
            ToAssigned(navController, appointmentId, sessionViewModel)
        }

        //Historial medico
        composable(
            route = NavRoutes.PetAppointment.route,
            arguments = listOf(
                navArgument("petId") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId") ?: ""
            PetAppointmentsScreen(navController = navController, petId = petId)
        }


        composable(route = NavRoutes.MedicalRecordDetail.route
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId") ?: ""
            val medicalRecordId = backStackEntry.arguments?.getString("medicalRecordId") ?:""
            Log.d("NavigationArgs", " medical record: $medicalRecordId") // <--- Agrega este Log
            PetRecordDetailScreen(navController = navController,petId = petId, medicalRecordId = medicalRecordId, sessionViewModel )
        }
        //cliente
        //Inicio CLiente
        composable(NavRoutes.HomeClient.route
        ) { HomeScreen(navController, sessionViewModel) }

        //Cita Cliente
        composable(NavRoutes.RegisterAppointment.route) {
            RegisterAppointment(navController, sessionViewModel)
        }


        composable(NavRoutes.MyPetAppointment.route) {
            MyPetAppointmentScreen(navController, sessionViewModel)
        }

        composable(NavRoutes.MyPetAssigned.route) {
            MyPetAssignedScreen(navController = navController)
        }

        composable(NavRoutes.RegisterPet.route) {
            RegisterPetScreen(navController)
        }

        composable(route = NavRoutes.MyPetDetail.route
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId") ?:""
            Log.d("NavigationArgs", " PET: $petId")
            MyPetDetail(navController = navController, petId = petId, sessionViewModel)
        }


        //inicio de tienda
        composable(NavRoutes.Shop.route) {
            ShopScreen(navController)
        }

        composable("shop") {
            ShopScreen(navController = navController)
        }
        //Productos de tienda
        composable("home_shop"
        ) {
            HomeShopScreen(
                navController = navController,
                sessionViewModel = sessionViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = NavRoutes.ProductDetail.route,
            arguments = listOf(
                navArgument("productId") {
                    nullable = false
                    type = NavType.StringType
                }
            ),
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""


            ProductDetailScreen(
                navController = navController,
                productId = productId,
                sessionViewModel = sessionViewModel, // si lo necesitas inyectado explícitamente
                onBack = { navController.popBackStack() }
            )
        }

        //carrito
        composable(
            route = NavRoutes.CartProductDetail.route,
            enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) }
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavRoutes.HomeShop.route) // 👈
            }

            CartProductDetailScreen(
                navController = navController,
                sessionViewModel = sessionViewModel,
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
                sessionViewModel = sessionViewModel,
                onBack = { navController.popBackStack() },
                onCancel = { navController.popBackStack() },
                onConfirm = {
                    navController.navigate(NavRoutes.HomeShop.route) {
                        popUpTo(NavRoutes.CartProductDetail.route) { inclusive = true }
                    }
                }
            )
        }
        

        //Administrador
        composable(NavRoutes.RechargeVitalCoins.route){
            RechargeVitalCoinsScreen(navController = navController, sessionViewModel = sessionViewModel)
        }

        composable(
            route = NavRoutes.AssignVetRol.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->

            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            AssignVeterinarianScreen(navController = navController, userId = userId, sessionViewModel = sessionViewModel)
        }
        composable(NavRoutes.AllVets.route) {
            AllVeterinarianScreen(navController = navController, sessionViewModel = sessionViewModel)
        }

        composable(NavRoutes.AllAppointments.route)
        { AllAppointmentsScreen(navController, sessionViewModel) }

        composable(NavRoutes.AdminHome.route)
        { AdminHomeScreen(navController = navController, sessionViewModel = sessionViewModel) }

        composable(NavRoutes.RedeemedCoins.route){
            RedeemedCoinsScreen(navController = navController, sessionViewModel=sessionViewModel)
        }

    }
}
