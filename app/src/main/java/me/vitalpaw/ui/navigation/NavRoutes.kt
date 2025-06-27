package me.vitalpaw.ui.navigation

sealed class NavRoutes(val route: String) {
    object Login : NavRoutes("login")
    object Register : NavRoutes("register")
    object Home : NavRoutes("assigned_appointments")
    object ToAssigned : NavRoutes("to_assigned")

    object AppointmentDetail : NavRoutes("appointment_detail/{appointmentId}") {
        fun createRoute(appointmentId: String) = "appointment_detail/$appointmentId"
    }

    object HomeShopScreen : NavRoutes("home_shop")

    // Cart declarado una sola vez
    object Cart : NavRoutes("cart")

    // ProductDetail con ruta dinámica (con parámetro)
    object ProductDetail : NavRoutes("product_detail/{productIndex}") {
        fun createRoute(productIndex: Int) = "product_detail/$productIndex"
    }

    object CartProductDetail : NavRoutes("cart_product_detail/{index}") {
        fun createRoute(index: Int) = "cart_product_detail/$index"
    }

    object ShopSplash : NavRoutes("shop_splash")
    object HomeShop : NavRoutes("home_shop")
    object ShopDetail : NavRoutes("shop_detail")
}
