package me.vitalpaw.ui.screens.administrador

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.vitalpaw.ui.components.admin.VeterinarianCard
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.admin.VeterinarianViewModel
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import me.vitalpaw.ui.components.buttons.SalirButton
import me.vitalpaw.ui.components.navigationBar.HomeTopBar
import me.vitalpaw.ui.components.navigationBar.RoleBasedDrawerScaffold
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.viewmodels.SessionViewModel
import androidx.compose.foundation.lazy.items

@Composable
fun AllVeterinarianScreen(
    navController: NavHostController,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    viewModel: VeterinarianViewModel = hiltViewModel()
) {
    val token by sessionViewModel.firebaseToken.collectAsState()
    val veterinarians by viewModel.veterinarians
    val isLoading by sessionViewModel.isLoading.collectAsState()

    LaunchedEffect(token) {
        token?.let {
            viewModel.loadVeterinarians(it)
        }
    }

    RoleBasedDrawerScaffold(
        sessionViewModel = sessionViewModel,
        navController = navController
    ) { onMenuClick ->

        Scaffold(
            topBar = {
                HomeTopBar(
                    title = "VETERINARIOS",
                    onMenuClick = onMenuClick,
                    onHomeClick = {
                        navController.navigate(NavRoutes.AdminHome.route) {
                            popUpTo(NavRoutes.Login.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                )
            },
            containerColor = Color.White
        ) { paddingValues ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    veterinarians.isEmpty() -> {
                        Text(
                            text = "No hay veterinarios registrados",
                            style = MaterialTheme.typography.titleMedium,
                            fontFamily = quicksandFont,
                            color = Color.Gray,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    else -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 15.dp, vertical = 18.dp)
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                contentPadding = PaddingValues(vertical = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                items(veterinarians) { user ->
                                    VeterinarianCard(
                                        user = user,
                                        onEditClick = {
                                            navController.navigate(
                                                NavRoutes.AssignVetRol.createRoute(user.id!!)
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}