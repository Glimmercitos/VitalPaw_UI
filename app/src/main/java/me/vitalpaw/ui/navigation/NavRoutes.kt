package me.vitalpaw.ui.navigation

sealed class NavRoutes(val route: String) {

    object Login : NavRoutes("login")

    object Bienvenido: NavRoutes("bienvenido")

    object Register : NavRoutes("register")

    object Home : NavRoutes("assigned_appointments")

    object ToAssigned : NavRoutes("to_assigned") {
    const val fullRoute = "to_assigned?appointmentId={appointmentId}"
    fun createRoute(appointmentId: String): String =
            "to_assigned?appointmentId=$appointmentId"
    }

    object PetAppointment: NavRoutes("pet_appointments/{petId}"){
        fun createRoute(petId: String) = "pet_appointments/$petId"
    }

    object AppointmentDetail {
        const val route = "appointment_detail?appointmentId={appointmentId}"
        fun createRoute(appointmentId: String) =
            "appointment_detail?appointmentId=$appointmentId"
    }

//    object PetAppointmentDetail : NavRoutes("pet_appointment_detail/{appointmentId}") {
//        fun createRoute(petAppointmentId: String) = "pet_appointment_detail/$petAppointmentId"
//    }

    object MedicalRecordDetail {
        const val route = "medicalRecord_detail?petId={petId}&medicalRecordId={medicalRecordId}"
        fun createRoute(petId: String, medicalRecordId: String): String =
            "medicalRecord_detail?petId=$petId&medicalRecordId=$medicalRecordId"
    }

}