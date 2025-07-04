package me.vitalpaw.ui.screens.cliente

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import me.vitalpaw.R
import me.vitalpaw.ui.components.navigationBar.RoleBasedDrawerScaffold
import me.vitalpaw.ui.components.topbar.TopBar
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodel.PetViewModel
import me.vitalpaw.viewmodels.SessionViewModel
import me.vitalpaw.ui.components.navigationBar.HomeTopBar

import me.vitalpaw.ui.components.navigationBar.RoleBasedDrawerScaffold


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPetDetail(
    navController: NavHostController,
    petId: String?,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    viewModel: PetViewModel = hiltViewModel()
) {
    val token by sessionViewModel.firebaseToken.collectAsState()
    val pets = viewModel.pets.collectAsState()

    LaunchedEffect(token) {
        if (pets.value.isEmpty()) {
            viewModel.loadMyPets(token!!)
        }
    }

    val pet = pets.value.find { it.id == petId }

    RoleBasedDrawerScaffold(
        sessionViewModel = sessionViewModel,
        navController = navController
    ) { onMenuClick ->

        Scaffold(
            topBar = {
                HomeTopBar(
                    title = "DETALLE DE MASCOTA",
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

            if (pet == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF3695B9))
                }
                return@Scaffold
            }

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fondo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        AsyncImage(
                            model = pet.imageUrl.takeIf { it.isNotBlank() } ?: R.drawable.petphoto,
                            contentDescription = "Foto de ${pet.name}",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(140.dp)
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        InfoField(label = "Nombre", value = pet.name)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            InfoField(label = "Especie", value = pet.species, modifier = Modifier.weight(1f))
                            InfoField(
                                label = "Edad",
                                value = "${pet.age} ${pet.unitAge ?: "años"}",
                                modifier = Modifier.weight(1f)
                            )
                            InfoField(
                                label = "Género",
                                value = if (pet.gender) "M" else "F",
                                modifier = Modifier.weight(1f)
                            )
                        }
                        InfoField(label = "Raza", value = pet.breed)
                        InfoField(label = "Peso", value = "${pet.weight} kg")
                    }
                }
            }
        }
    }
}


@Composable
fun InfoField(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontFamily = quicksandFont,
            color = Color.Gray,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp))
                .border(1.dp, Color(0xFF90CAF9), RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Text(
                text = value,
                fontSize = 16.sp,
                fontFamily = quicksandFont,
                color = Color.DarkGray
            )
        }
    }
}