package me.vitalpaw.ui.navigation

sealed class NavRoutes(val route: String) {
    object Login : NavRoutes("login")
    object Bienvenido: NavRoutes("bienvenido")
    object Register : NavRoutes("register")
<<<<<<< HEAD
    object Home : NavRoutes("assigned_appointments/{token}") {
        fun createRoute(token: String) = "assigned_appointments/$token"
    }


    object ToAssigned : NavRoutes("to_assigned") {
        fun createRoute(appointmentId: String, token: String): String {
            return "to_assigned?appointmentId=$appointmentId&token=$token"
        }

        const val fullRoute = "to_assigned?appointmentId={appointmentId}&token={token}"
    }


    object AppointmentDetail {
        const val route = "appointment_detail?appointmentId={appointmentId}&token={token}"

        fun createRoute(appointmentId: String, token: String) =
            "appointment_detail?appointmentId=$appointmentId&token=$token"
=======
    object Home : NavRoutes("assigned_appointments")
    object ToAssigned : NavRoutes("to_assigned")
    object PetAppointment: NavRoutes("pet_appointments/{petId}"){
        fun createRoute(petId: String) = "pet_appointments/$petId"
    }
    object AppointmentDetail : NavRoutes("appointment_detail/{appointmentId}"){
        fun createRoute(appointmentId: String) = "appointment_detail/$appointmentId"
>>>>>>> 908a8562511bdeebcd495f3bfdab885ea0e4d8c7
    }
    object PetAppointmentDetail : NavRoutes("pet_appointment_detail/{appointmentId}") {
        fun createRoute(petAppointmentId: String) = "pet_appointment_detail/$petAppointmentId"
    }
}
