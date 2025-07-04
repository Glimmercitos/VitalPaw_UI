package me.vitalpaw.ui.components.navigationBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.viewmodels.SessionViewModel
import androidx.compose.runtime.getValue


@Composable
fun RoleBasedDrawerScaffold(
    sessionViewModel: SessionViewModel,
    navController: NavHostController,
    content: @Composable (onMenuClick: () -> Unit) -> Unit
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val role by sessionViewModel.userRole.collectAsState()

    val onLogoutClick = {
        sessionViewModel.logout()
        navController.navigate(NavRoutes.Login.route) {
            popUpTo(0) { inclusive = true }
        }
    }
    val menuItems = getMenuItemsByRole(role, onLogoutClick)

    println("ROL ACTUAL: $role")
//    println("ITEMS DEL MENÚ: ${getMenuItemsByRole(role)}")


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SideMenuDrawer(
                items = menuItems,
                onItemClick = { item ->item.onClick?.invoke()
                    ?: item.route?.let { navController.navigate(it) }

                    scope.launch { drawerState.close() }
                },
                modifier = Modifier.fillMaxWidth(0.6f)
            )
        }
    ) {
        content {
            scope.launch { drawerState.open() }
        }
    }
}
