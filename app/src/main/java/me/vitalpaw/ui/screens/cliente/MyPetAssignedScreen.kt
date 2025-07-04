package me.vitalpaw.ui.screens.cliente

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.vitalpaw.R
import me.vitalpaw.ui.components.buttons.SalirButton
import me.vitalpaw.ui.components.cards.AppointmentPetCard
import me.vitalpaw.ui.components.navigationBar.HomeTopBar
import me.vitalpaw.ui.components.navigationBar.RoleBasedDrawerScaffold
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.SessionViewModel
import me.vitalpaw.viewmodel.PetViewModel

@Composable
fun MyPetAssignedScreen(
    navController: NavHostController,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    viewModel: PetViewModel = hiltViewModel()
) {
    val token by sessionViewModel.firebaseToken.collectAsState()
    val pets = viewModel.pets.collectAsState()
    val petList = pets.value
    val context = LocalContext.current

    LaunchedEffect(token) {
        if (token.isNullOrBlank()) {
            sessionViewModel.loadUserData()
        } else {
            viewModel.loadMyPets(token!!)
        }
    }

    RoleBasedDrawerScaffold(
        sessionViewModel = sessionViewModel,
        navController = navController
    ) { onMenuClick ->

        Scaffold(
            topBar = {
                HomeTopBar(
                    title = "MIS MASCOTAS",
                    onMenuClick = onMenuClick,
                    onHomeClick = {
                        navController.navigate(NavRoutes.HomeClient.route) {
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
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fondo),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
//
//                    Text(
//                        text = "MIS MASCOTAS",
//                        style = MaterialTheme.typography.bodyMedium,
//                        fontFamily = quicksandFont,
//                        color = Color.Gray,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(
//                                color = Color(0xFFF5F5F5),
//                                shape = RoundedCornerShape(25.dp)
//                            )
//                            .padding(vertical = 20.dp),
//                        textAlign = TextAlign.Center
//                    )

                    if (petList.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Perfil de mascotas vacía",
                                style = MaterialTheme.typography.titleMedium.copy(fontFamily = quicksandFont),
                                color = Color.LightGray,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    } else {
                        petList.forEach { pet ->
                            AppointmentPetCard(
                                pet = pet,
                                onArrowClick = {
                                    navController.navigate(NavRoutes.MyPetDetail.createRoute(pet.id))
                                },
                                onDeleteClick = {
                                    token?.let {
                                        viewModel.deleteClientPet(it, pet.id)
                                        Toast.makeText(
                                            context,
                                            "Perfil eliminado correctamente",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } ?: Toast.makeText(
                                        context,
                                        "Token no disponible",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(100.dp))
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 24.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    SalirButton {
                        navController.navigate(NavRoutes.HomeClient.route) {
                            popUpTo(NavRoutes.MyPetAssigned.route) { inclusive = true }
                        }
                    }
                }
            }
        }
    }
}
