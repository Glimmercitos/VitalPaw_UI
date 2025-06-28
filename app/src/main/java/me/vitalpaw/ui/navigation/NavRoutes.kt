package me.vitalpaw.ui.navigation

sealed class NavRoutes(val route: String) {
    object Login : NavRoutes("login")
    object Register : NavRoutes("register")
    object Home : NavRoutes("assigned_appointments")
    object ToAssigned : NavRoutes("to_assigned")

    object AppointmentDetail : NavRoutes("appointment_detail/{appointmentId}") {
        fun createRoute(appointmentId: String) = "appointment_detail/$appointmentId"
    }

    object HomeClient : NavRoutes("home_client")
    object Shop : NavRoutes("shop")
    object MyPetAssigned : NavRoutes("my_pet_assigned")
    object RegisterAppointment : NavRoutes("register_appointment")
    object RegisterPet : NavRoutes("register_pet")
    object MyPetAppointment : NavRoutes("my_pet_appointment")


}