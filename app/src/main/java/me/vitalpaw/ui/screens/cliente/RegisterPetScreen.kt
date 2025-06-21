@file:OptIn(ExperimentalMaterial3Api::class)

package me.vitalpaw.ui.screens.cliente

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import me.vitalpaw.R
import me.vitalpaw.ui.components.buttons.GuardarCitaButton
import me.vitalpaw.ui.components.buttons.SalirButton
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.RegisterPetViewModel
import kotlinx.coroutines.flow.StateFlow

@Preview(showBackground = true)
@Composable
fun PreviewRegisterPetScreen() {
    RegisterPetScreen(navController = NavHostController(LocalContext.current))
}

@Composable
fun <T> rememberStateFromFlow(flow: StateFlow<T>): State<T> {
    val state = remember { mutableStateOf(flow.value) }
    LaunchedEffect(flow) {
        flow.collect { state.value = it }
    }
    return state
}

@Composable
fun RegisterPetScreen(navController: NavController, viewModel: RegisterPetViewModel = viewModel()) {
    val imageUriState = rememberStateFromFlow(viewModel.imageUri)
    val nameState = rememberStateFromFlow(viewModel.name)
    val speciesState = rememberStateFromFlow(viewModel.species)
    val ageState = rememberStateFromFlow(viewModel.age)
    val genderState = rememberStateFromFlow(viewModel.gender)
    val breedState = rememberStateFromFlow(viewModel.breed)

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.onImageChange(it)
        }
    }

    val showSuccess = remember { mutableStateOf(false) }
    val expandedSpecies = remember { mutableStateOf(false) }
    val expandedGender = remember { mutableStateOf(false) }

    val speciesOptions = listOf("Perro", "Gato", "Conejo", "Ave", "Otro")
    val genderOptions = listOf("Macho", "Hembra")
    val context = LocalContext.current
    val calendar = remember { java.util.Calendar.getInstance() }
    val dateFormatter = remember { java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault()) }
    val date = remember { java.util.Calendar.getInstance() }
    val showDatePicker = remember { mutableStateOf(false) }

    val birthDate = rememberStateFromFlow(viewModel.age)

    if (showDatePicker.value) {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                date.set(year, month, dayOfMonth)
                val formatted = dateFormatter.format(date.time)
                viewModel.onAgeChange(formatted)
                showDatePicker.value = false
            },
            date.get(java.util.Calendar.YEAR),
            date.get(java.util.Calendar.MONTH),
            date.get(java.util.Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.maxDate = System.currentTimeMillis() // no permite fechas futuras
        }.show()
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "REGISTRA TU MASCOTA",
                    fontSize = 16.sp,
                    fontFamily = quicksandFont,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp), // espacio suficiente para ícono
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

                    // Ícono flotante arriba a la derecha
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
                            .clickable {
                                launcher.launch("image/*")
                            }
                            .padding(6.dp)
                            .size(25.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                            unfocusedBorderColor = Color(0xFF6E7AE6)
                        ),
                        textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont)
                    )
                    ExposedDropdownMenu(
                        expanded = expandedSpecies.value,
                        onDismissRequest = { expandedSpecies.value = false }
                    ) {
                        speciesOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option, fontFamily = quicksandFont) },
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
                    value = birthDate.value,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text("Fecha de nacimiento", fontFamily = quicksandFont, color = TextGray)
                    },
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker.value = true }) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
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
                            unfocusedBorderColor = Color(0xFF6E7AE6)
                        ),
                        textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont)
                    )
                    ExposedDropdownMenu(
                        expanded = expandedGender.value,
                        onDismissRequest = { expandedGender.value = false }
                    ) {
                        genderOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option, fontFamily = quicksandFont) },
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

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SalirButton { navController.popBackStack() }
                    GuardarCitaButton { showSuccess.value = true }
                }

                if (showSuccess.value) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Éxito",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Perfil de mascota creado con éxito",
                            color = Color(0xFF4CAF50),
                            fontFamily = quicksandFont
                        )
                    }
                }
            }
        }
    }
