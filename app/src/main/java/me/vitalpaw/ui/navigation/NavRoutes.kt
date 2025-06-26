package me.vitalpaw.ui.navigation

sealed class NavRoutes(val route: String) {
    object Login : NavRoutes("login")

    object Bienvenido: NavRoutes("bienvenido/{token}"){
        fun createRoute(token: String) = "bienvenido/$token"
    }

    object Register : NavRoutes("register")

    object Home : NavRoutes("assigned_appointments/{token}") {
        fun createRoute(token: String) = "assigned_appointments/$token"
    }


    object ToAssigned : NavRoutes("to_assigned") {
        fun createRoute(appointmentId: String, token: String): String {
            return "to_assigned?appointmentId=$appointmentId&token=$token"
        }

        const val fullRoute = "to_assigned?appointmentId={appointmentId}&token={token}"
    }

    object PetAppointment: NavRoutes("pet_appointments/{petId}/{token}"){
        fun createRoute(petId: String, token: String) = "pet_appointments/$petId/$token"
    }


    object AppointmentDetail {
        const val route = "appointment_detail?appointmentId={appointmentId}&token={token}"

        fun createRoute(appointmentId: String, token: String) =
            "appointment_detail?appointmentId=$appointmentId&token=$token"
    }

    object PetAppointmentDetail : NavRoutes("pet_appointment_detail/{appointmentId}") {
        fun createRoute(petAppointmentId: String) = "pet_appointment_detail/$petAppointmentId"
    }
}