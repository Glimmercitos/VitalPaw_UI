package me.vitalpaw.ui.navigation

sealed class NavRoutes(val route: String) {
    object Login : NavRoutes("login")
    object Register : NavRoutes("register")
    object Home : NavRoutes("home")
    object AssignedAppointments : NavRoutes("assigned_appointments")
    object ToAssigned : NavRoutes("to_assigned")
    object Grooming : NavRoutes("grooming")
}
