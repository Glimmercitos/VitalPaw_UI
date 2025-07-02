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
    //Admin
    object AdminHome : NavRoutes("all_appointments")
    object AllVets : NavRoutes("edit_vet_rol/{userId}") {
        fun createRoute(userId: String) = "edit_vet_rol/$userId"
    }
    object AssignVetRol : NavRoutes("assign_vet_rol/{userId}"){
        fun createRoute(userId : String) = "assign_vet_rol/$userId"
    }
    object RechargeVitalCoins : NavRoutes("recharge_vital_coins")
    object RedeemedCoins : NavRoutes("redeemed_coins")
}
