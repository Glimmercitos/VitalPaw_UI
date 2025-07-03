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

    object RegisterAppointment : NavRoutes("register_appointment")

    object MyPetAppointment : NavRoutes("my_pet_appointment")

    object ProductDetail {
        const val route = "product_detail?productId={productId}"

        fun createRoute(productId: String): String {
            return "product_detail?productId=$productId"
        }
    }


    //Cliente

    object HomeClient : NavRoutes("home_client")
    object Shop : NavRoutes("shop")
    object MyPetAssigned : NavRoutes("my_pet_assigned")

    object MyPetDetail {
        const val route = "pet_detail?petId={petId}"
        fun createRoute(petId: String) =
            "pet_detail?petId=$petId"
    }

    object RegisterPet : NavRoutes("register_pet")
    object HomeShop : NavRoutes("home_shop")



    object CartProductDetail : NavRoutes("cartProductDetail")
    object CartRedeemDetail : NavRoutes("cart_redeem_detail")

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
}