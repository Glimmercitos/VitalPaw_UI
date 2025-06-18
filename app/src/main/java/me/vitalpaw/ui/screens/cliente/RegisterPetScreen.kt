package me.vitalpaw.ui.screens.cliente

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import me.vitalpaw.R
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.screens.veterinario.ToAssigned
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.RegisterPetViewModel

@Preview(showBackground = true)
@Composable
fun PreviewRegisterPetScreen() {
    RegisterPetScreen(navController = NavHostController(LocalContext.current))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPetScreen(navController: NavController, viewModel: RegisterPetViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // TopBar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(Color(0xFFF5F5F5))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "REGISTRA TU MASCOTA",
                fontSize = 14.sp,
                fontFamily = quicksandFont
            )
            Icon(Icons.Default.Menu, contentDescription = "Menu")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Imagen editable
        Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.petphoto),
                contentDescription = "Pet Photo",
                modifier = Modifier
                    .size(140.dp)
                    .align(Alignment.TopCenter)
                    .clip(CircleShape)
                    .background(Color(0xFFD6E4F0))
            )
            Icon(
                Icons.Default.Edit,
                contentDescription = "Edit",
                tint = Color(0xFF7C6AFF),
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = (-36).dp, y = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campos
        OutlinedTextField(
            value = viewModel.name.collectAsState().value,
            onValueChange = viewModel::onNameChange,
            label = { Text("Nombre mascota") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = viewModel.species.collectAsState().value,
                onValueChange = viewModel::onSpeciesChange,
                label = { Text("Especie") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            )
            OutlinedTextField(
                value = viewModel.age.collectAsState().value,
                onValueChange = viewModel::onAgeChange,
                label = { Text("Edad") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            )
            OutlinedTextField(
                value = viewModel.gender.collectAsState().value,
                onValueChange = viewModel::onGenderChange,
                label = { Text("Género") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Información Adicional",
            color = Color(0xFF19486D),
            fontSize = 16.sp,
            fontFamily = quicksandFont
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.breed.collectAsState().value,
            onValueChange = viewModel::onBreedChange,
            label = { Text("Raza") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.weight.collectAsState().value,
            onValueChange = viewModel::onWeightChange,
            label = { Text("Peso (kg)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = { navController.popBackStack() },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCBDFF4))
            ) {
                Text("SALIR", fontFamily = quicksandFont, color = Color(0xFF19486D))
            }
            Button(
                onClick = {
                    // Guardar acción
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCBDFF4))
            ) {
                Text("GUARDAR", fontFamily = quicksandFont, color = Color(0xFF19486D))
            }
        }
    }
}
