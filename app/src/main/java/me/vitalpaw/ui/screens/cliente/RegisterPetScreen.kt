@file:OptIn(ExperimentalMaterial3Api::class)

package me.vitalpaw.ui.screens.cliente

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.StateFlow
import me.vitalpaw.R
import me.vitalpaw.ui.components.buttons.GuardarCitaButton
import me.vitalpaw.ui.components.buttons.GuardarMascota
import me.vitalpaw.ui.components.buttons.SalirButton
import me.vitalpaw.ui.components.modal.ConfirmationDialog
import me.vitalpaw.ui.components.topbar.TopBar
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.SessionViewModel
import me.vitalpaw.viewmodels.cliente.RegisterPetViewModel

//@Preview(showBackground = true)
//@Composable
//fun PreviewRegisterPetScreen() {
//    RegisterPetScreen(navController = NavHostController(LocalContext.current))
//}

@Composable
fun <T> rememberStateFromFlow(flow: StateFlow<T>): State<T> {
    val state = remember { mutableStateOf(flow.value) }
    LaunchedEffect(flow) {
        flow.collect { state.value = it }
    }
    return state
}

@Composable
fun RegisterPetScreen(
    navController: NavController,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    viewModel: RegisterPetViewModel = hiltViewModel()
) {
    val imageUriState = rememberStateFromFlow(viewModel.imageUri)
    val nameState = rememberStateFromFlow(viewModel.name)
    val speciesState = rememberStateFromFlow(viewModel.species)
    val genderState = rememberStateFromFlow(viewModel.gender)
    val breedState = rememberStateFromFlow(viewModel.breed)
    val birthDateState = rememberStateFromFlow(viewModel.birthDate)
    val weightState = rememberStateFromFlow(viewModel.weight)

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { viewModel.onImageChange(it) }
    }

    var showSuccessDialog by remember { mutableStateOf(false) }
    var errorDialogMessage by remember { mutableStateOf<String?>(null) }

    val expandedSpecies = remember { mutableStateOf(false) }
    val expandedGender = remember { mutableStateOf(false) }

    val speciesOptions = listOf(
        "Perro", "Gato", "Conejo", "Ave", "Hámster", "Tortuga", "Pez", "Iguana", "Serpiente", "Otro"
    )
    val genderOptions = listOf("Macho", "Hembra")

    val context = LocalContext.current

    val calendar = remember { java.util.Calendar.getInstance() }
    val dateFormatter =
        remember { java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault()) }
    val date = remember { java.util.Calendar.getInstance() }
    val showDatePicker = remember { mutableStateOf(false) }
    val isSubmitting by rememberStateFromFlow(viewModel.isSubmitting)

    if (showDatePicker.value) {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                date.set(year, month, dayOfMonth)
                viewModel.onBirthDateChange(dateFormatter.format(date.time))
                showDatePicker.value = false
            },
            date.get(java.util.Calendar.YEAR),
            date.get(java.util.Calendar.MONTH),
            date.get(java.util.Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.maxDate = System.currentTimeMillis()
        }.show()
    }

    fun handleSave() {
        if (viewModel.name.value.isBlank() ||
            viewModel.species.value.isBlank() ||
            viewModel.gender.value.isBlank() ||
            viewModel.birthDate.value.isBlank()
        ) {
            errorDialogMessage = "Por favor, completa todos los campos obligatorios."
            return
        }

        viewModel.createPet(
            context,
            onSuccess = {
                showSuccessDialog = true
            },
            onError = { msg ->
                errorDialogMessage = msg
            }
        )
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            TopBar(
                title = "REGISTRA TU MASCOTA",
                onBackClick = { navController.popBackStack() }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(140.dp)
                ) {
                    val imageModifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .background(Color.White, shape = CircleShape)
                        .border(2.dp, Color.White, CircleShape)

                    if (imageUriState.value != null) {
                        Image(
                            painter = rememberAsyncImagePainter(model = imageUriState.value),
                            contentDescription = "Pet Photo",
                            modifier = imageModifier,
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.petphoto),
                            contentDescription = "Default Pet Photo",
                            modifier = imageModifier,
                            contentScale = ContentScale.Crop
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar imagen",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 3.dp, y = (-3).dp)
                            .graphicsLayer {
                                shadowElevation = 8f
                                shape = CircleShape
                                clip = true
                            }
                            .background(Color(0xFF6E7AE6), shape = CircleShape)
                            .clickable { launcher.launch("image/*") }
                            .padding(6.dp)
                            .size(25.dp)
                    )
                }
            }

            OutlinedTextField(
                value = nameState.value,
                onValueChange = viewModel::onNameChange,
                label = { Text("Nombre mascota", fontFamily = quicksandFont) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6E7AE6),
                    unfocusedBorderColor = Color(0xFF6E7AE6)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(expanded = expandedSpecies.value, onExpandedChange = {
                expandedSpecies.value = !expandedSpecies.value
            }) {
                OutlinedTextField(
                    value = speciesState.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Especie", fontFamily = quicksandFont) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSpecies.value)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFF6E7AE6),
                        unfocusedBorderColor = Color(0xFF6E7AE6),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        fontFamily = quicksandFont,
                        color = Color.Black
                    )
                )
                ExposedDropdownMenu(
                    expanded = expandedSpecies.value,
                    onDismissRequest = { expandedSpecies.value = false },
                    modifier = Modifier.background(Color.White, shape = RoundedCornerShape(12.dp))
                ) {
                    speciesOptions.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    option,
                                    fontFamily = quicksandFont,
                                    color = Color.Black
                                )
                            },
                            onClick = {
                                viewModel.onSpeciesChange(option)
                                expandedSpecies.value = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = birthDateState.value,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Fecha de nacimiento", fontFamily = quicksandFont, color = TextGray)
                },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker.value = true }) {
                        Icon(
                            Icons.Default.CalendarToday,
                            contentDescription = "Fecha",
                            tint = PrimaryBlue
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = PrimaryBlue,
                    cursorColor = PrimaryBlue
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontFamily = quicksandFont,
                    fontSize = 16.sp,
                    color = TextGray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(expanded = expandedGender.value, onExpandedChange = {
                expandedGender.value = !expandedGender.value
            }) {
                OutlinedTextField(
                    value = genderState.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Género", fontFamily = quicksandFont) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGender.value)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFF6E7AE6),
                        unfocusedBorderColor = Color(0xFF6E7AE6),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        fontFamily = quicksandFont,
                        color = Color.Black
                    )
                )
                ExposedDropdownMenu(
                    expanded = expandedGender.value,
                    onDismissRequest = { expandedGender.value = false },
                    modifier = Modifier.background(Color.White, shape = RoundedCornerShape(12.dp))
                ) {
                    genderOptions.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    option,
                                    fontFamily = quicksandFont,
                                    color = Color.Black
                                )
                            },
                            onClick = {
                                viewModel.onGenderChange(option)
                                expandedGender.value = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Información adicional",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = quicksandFont,
                color = Color(0xFF19486D)
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = breedState.value,
                onValueChange = viewModel::onBreedChange,
                label = { Text("Raza", fontFamily = quicksandFont) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6E7AE6),
                    unfocusedBorderColor = Color(0xFF6E7AE6)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = weightState.value,
                onValueChange = viewModel::onWeightChange,
                label = { Text("Peso", fontFamily = quicksandFont) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6E7AE6),
                    unfocusedBorderColor = Color(0xFF6E7AE6)
                ),
                textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SalirButton {
                    navController.navigate(NavRoutes.HomeClient.route) {
                        popUpTo(NavRoutes.RegisterPet.route) { inclusive = true }
                    }
                }

                GuardarMascota(
                    enabled = !isSubmitting,
                    onClick = { handleSave() }
                )

            }

        }

        ConfirmationDialog(
            show = showSuccessDialog,
            onDismiss = {
                showSuccessDialog = false
                navController.navigate(NavRoutes.HomeClient.route) {
                    popUpTo(NavRoutes.RegisterPet.route) { inclusive = true }
                }
            },
            Title = "¡Éxito!",
            Message = "Mascota registrada correctamente"
        )

        ConfirmationDialog(
            show = errorDialogMessage != null,
            onDismiss = { errorDialogMessage = null },
            Title = "Error",
            Message = errorDialogMessage ?: "Error desconocido"
        )

    }
}