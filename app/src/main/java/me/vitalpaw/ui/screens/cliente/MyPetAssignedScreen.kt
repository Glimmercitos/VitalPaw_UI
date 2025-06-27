package me.vitalpaw.ui.screens.cliente

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.vitalpaw.R
import me.vitalpaw.models.Pet
import me.vitalpaw.models.User
import me.vitalpaw.ui.components.buttons.SalirButton
import me.vitalpaw.ui.components.cards.AppointmentPetCard
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.cliente.MyPetAssignedViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MyPetAssignedScreen(
    navController: NavController,
    viewModel: MyPetAssignedViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadAssignedPets()
    }

    val assignedPets = viewModel.assignedPets.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
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

            Text(
                text = "MIS MASCOTAS",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = quicksandFont,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .padding(vertical = 20.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (assignedPets.isEmpty()) {
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
                assignedPets.forEach { pet ->
                    AppointmentPetCard(
                        pet = pet,
                        onClick = { },
                        onDeleteClick = {
                            viewModel.deleteAssignedPet(pet.id ?: "")
                            Toast.makeText(context, "Perfil eliminado correctamente", Toast.LENGTH_SHORT).show()
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
                navController.navigate("register_pet") {
                    popUpTo("my_pet_assigned") { inclusive = true }
                }
            }
        }
    }
}


class PreviewMyPetAssignedViewModel : ViewModel() {
    private val _assignedPets = mutableStateListOf(
        Pet(
            id = "1",
            name = "Toby",
            species = "Perro",
            age = 4,
            breed = "Labrador",
            weight = 20.0,
            gender = true,
            unitAge = "años",
            owner = User("u1", "g123", "Dueño", "email@example.com"),
            imageRes = R.drawable.dog1
        ),
        Pet(
            id = "2",
            name = "Luna",
            species = "Gato",
            age = 2,
            breed = "Persa",
            weight = 4.5,
            gender = false,
            unitAge = "años",
            owner = User("u2", "g124", "Dueña", "luna@example.com"),
            imageRes = R.drawable.gato1
        )
    )

    val assignedPets: List<Pet> get() = _assignedPets

    fun deleteAssignedPet(petId: String) {
        _assignedPets.removeAll { it.id == petId }
    }

    fun loadAssignedPets() {
        // No-op en preview
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyPetAssignedScreen() {
    val navController = rememberNavController()
    val previewViewModel = remember { PreviewMyPetAssignedViewModel() }

    Box(modifier = Modifier.fillMaxSize()) {
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "MIS MASCOTAS",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = quicksandFont,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .padding(vertical = 24.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))

            if (previewViewModel.assignedPets.isEmpty()) {
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
                previewViewModel.assignedPets.forEach { pet ->
                    AppointmentPetCard(
                        pet = pet,
                        onClick = {},
                        onDeleteClick = {
                            previewViewModel.deleteAssignedPet(pet.id ?: "")
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
            SalirButton(onClick = {})
        }
    }
}
