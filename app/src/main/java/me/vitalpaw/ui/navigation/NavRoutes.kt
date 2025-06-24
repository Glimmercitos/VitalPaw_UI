package me.vitalpaw.ui.navigation

sealed class NavRoutes(val route: String) {
    object Login : NavRoutes("login")
    object Bienvenido: NavRoutes("bienvenido")
    object Register : NavRoutes("register")
    object Home : NavRoutes("assigned_appointments")
    object ToAssigned : NavRoutes("to_assigned")
    object PetAppointment: NavRoutes("pet_appointments/{petId}"){
        fun createRoute(petId: String) = "pet_appointments/$petId"
    }
    object AppointmentDetail : NavRoutes("appointment_detail/{appointmentId}"){
        fun createRoute(appointmentId: String) = "appointment_detail/$appointmentId"
    }
    object PetAppointmentDetail : NavRoutes("pet_appointment_detail/{appointmentId}") {
        fun createRoute(petAppointmentId: String) = "pet_appointment_detail/$petAppointmentId"
    }
}
