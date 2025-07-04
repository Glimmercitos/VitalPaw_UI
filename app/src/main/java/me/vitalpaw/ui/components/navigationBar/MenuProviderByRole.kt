package me.vitalpaw.ui.components.navigationBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.hilt.navigation.compose.hiltViewModel
import me.vitalpaw.models.DrawerItem
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.viewmodels.SessionViewModel

fun getMenuItemsByRole(role: String, onLogoutClick: () -> Unit): List<DrawerItem> {
    return when (role) {
        "admin" -> listOf(
            DrawerItem("Inicio", Icons.Default.Home, NavRoutes.AdminHome.route),
            DrawerItem("Asignar veterinario", Icons.Default.HealthAndSafety, NavRoutes.AssignVetRol.route),
            DrawerItem("Veterinarios", Icons.Default.People, NavRoutes.AllVets.route),
            DrawerItem("Citas", Icons.Default.Event, NavRoutes.AllAppointments.route),
            DrawerItem("Recargar monedas", Icons.Default.AttachMoney, NavRoutes.RechargeVitalCoins.route),
            DrawerItem("Ganancias", Icons.Default.Check, NavRoutes.RedeemedCoins.route),
            DrawerItem("Cerrar sesión", Icons.Default.Logout, route = null, onClick = onLogoutClick)
        )
        "cliente" -> listOf(
            DrawerItem("Inicio", Icons.Default.Home, NavRoutes.HomeClient.route),
            DrawerItem("Registrar Mascota", Icons.Default.AppRegistration, NavRoutes.RegisterPet.route),
            DrawerItem("Mis Mascotas", Icons.Default.Pets, NavRoutes.MyPetAssigned.route),
            DrawerItem("Agendar Cita", Icons.Default.Schedule, NavRoutes.RegisterAppointment.route),
            DrawerItem("Mis Citas", Icons.Default.MedicalServices, NavRoutes.MyPetAppointment.route),
            DrawerItem("Tienda", Icons.Default.ShoppingBag, NavRoutes.Shop.route),
            DrawerItem("Cerrar sesión", Icons.Default.Logout, route = null, onClick = onLogoutClick)
        )
        else -> emptyList()
    }
}
