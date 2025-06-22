package me.vitalpaw.ui.navigation

sealed class NavRoutes(val route: String) {
    object Login : NavRoutes("login")
    object Register : NavRoutes("register")
    object Home : NavRoutes("assigned_appointments/{token}") {
        fun createRoute(token: String) = "assigned_appointments/$token"
    }

    object ToAssigned : NavRoutes("to_assigned")
    object AppointmentDetail {
        const val route = "appointment_detail?appointmentId={appointmentId}&token={token}"

        fun createRoute(appointmentId: String, token: String) =
            "appointment_detail?appointmentId=$appointmentId&token=$token"
    }
}
