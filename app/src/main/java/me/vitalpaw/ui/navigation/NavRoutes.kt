package me.vitalpaw.ui.navigation

sealed class NavRoutes(val route: String) {

    // Auth
    object Login : NavRoutes("login")
    object Register : NavRoutes("register")

    // Cliente
    object HomeClient : NavRoutes("home_client")
    object MyPetAssigned : NavRoutes("my_pet_assigned")
    object RegisterAppointment : NavRoutes("register_appointment")
    object MyPetAppointment : NavRoutes("my_pet_appointment")
    object RegisterPet : NavRoutes("register_pet")
    object MyPetDetail : NavRoutes("pet_detail/{petId}") {
        fun createRoute(petId: String) = "pet_detail/$petId"
    }


    // Tienda
    object Shop : NavRoutes("shop")
    object HomeShop : NavRoutes("home_shop")
    object ProductDetail : NavRoutes("product_detail/{index}") {
        fun createRoute(index: Int) = "product_detail/$index"
    }
    object CartProductDetail : NavRoutes("cartProductDetail")
    object CartRedeemDetail : NavRoutes("cart_redeem_detail") // ✅ Lleva a ShopDetailScreen

    // Veterinario
    object ToAssigned : NavRoutes("to_assigned")
    object Home : NavRoutes("assigned_appointments")
    object AppointmentDetail : NavRoutes("appointment_detail/{appointmentId}") {
        fun createRoute(appointmentId: String) = "appointment_detail/$appointmentId"
    }
}
