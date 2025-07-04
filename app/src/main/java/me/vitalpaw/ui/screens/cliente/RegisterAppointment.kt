package me.vitalpaw.ui.screens.cliente

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import me.vitalpaw.R
import me.vitalpaw.models.Pet
import me.vitalpaw.ui.components.buttons.CancelarCitaButton
import me.vitalpaw.ui.components.buttons.GuardarCitaButton
import me.vitalpaw.ui.components.icons.TimePickerDialog
import me.vitalpaw.ui.components.modal.ConfirmationDialog
import me.vitalpaw.ui.components.modal.ErrorDialog
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodel.PetViewModel
import me.vitalpaw.viewmodels.SessionViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.util.Log
import androidx.compose.material3.Scaffold
import me.vitalpaw.ui.components.navigationBar.HomeTopBar
import me.vitalpaw.ui.components.navigationBar.RoleBasedDrawerScaffold
import me.vitalpaw.viewmodels.veterinario.ToAssignedViewModel

val PrimaryBlue = Color(0xFF6E7AE6)
val TextGray = Color(0xFF606060)

@Preview(showBackground = true)
@Composable
fun PreviewRegisterAppointment() {
    RegisterAppointment(navController = NavHostController(LocalContext.current))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterAppointment(
    navController: NavHostController,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    petViewModel: PetViewModel = hiltViewModel(),
    viewModel: ToAssignedViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val token by sessionViewModel.firebaseToken.collectAsState()


    val pets by petViewModel.pets.collectAsState()
    LaunchedEffect(token, pets) {
        token?.let {
            Log.d("RegisterAppointment", "Token recibido: $it")
            petViewModel.loadMyPets(it)
            Log.d("RegisterAppointment", "Mascotas en pantalla: $pets")
        }
    }
    val petError by petViewModel.error.collectAsState()
    val petLoading by petViewModel.isLoading.collectAsState()

    val service by viewModel.selectedService.collectAsState()
    val description by viewModel.description.collectAsState()
    val date by viewModel.selectedDate.collectAsState()
    val time by viewModel.selectedTime.collectAsState()
    val selectedPet by viewModel.selectedPet.collectAsState()

    val dateFormatter = remember { SimpleDateFormat("yy/MM/dd", Locale.getDefault()) }
    val timeFormatter = remember { SimpleDateFormat("h:mm a", Locale.getDefault()) }

    var expandedPets by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    var errorTitle by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var successTitle by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    val serviceOptions = listOf("grooming", "consulta médica", "emergencias")
    var expanded by remember { mutableStateOf(false) }

    val hasDateSelected = date.timeInMillis != 0L
    val hasTimeSelected = time.timeInMillis != 0L

    fun handleSave() {
        if (viewModel.description.value.isBlank() || viewModel.selectedService.value.isBlank() || viewModel.selectedPet.value == null || !hasDateSelected || !hasTimeSelected) {
            errorTitle = "Error al asignar cita"
            errorMessage = "Campos vacíos"
            showErrorDialog = true
            return
        }

        viewModel.createAppointment() {
            successTitle = "Cita guardada!"
            successMessage = "Cita agendada correctamente."
            showSuccessDialog = true

        }
    }

    RoleBasedDrawerScaffold(
        sessionViewModel = sessionViewModel,
        navController = navController
    ) { onMenuClick ->

        LaunchedEffect(Unit) {
            sessionViewModel.loadUserData()
        }

        Scaffold(
            topBar = {
                HomeTopBar(
                    title = "REGISTRAR CITA",
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
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                Image(
                    painter = painterResource(id = R.drawable.fondo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                ) {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        IconButton(onClick = {
//                            navController.navigate(NavRoutes.ToAssigned.route) {
//                                popUpTo(NavRoutes.RegisterAppointment.route) { inclusive = true }
//                            }
//                        }) {
//                            Icon(
//                                Icons.Default.ArrowBack,
//                                contentDescription = "Atrás",
//                                tint = TextGray
//                            )
//                        }
//                        Text(
//                            text = "REGISTRAR CITA",
//                            fontFamily = quicksandFont,
//                            fontSize = 20.sp,
//                            color = TextGray,
//                            fontWeight = FontWeight.Medium
//                        )
//                        IconButton(onClick = { /* Menú futuro */ }) {
//                            Icon(Icons.Default.Menu, contentDescription = "Menú", tint = TextGray)
//                        }
//                    }

                    // --- AÑADE EL SELECTOR DE MASCOTA PRIMERO ---
                    ExposedDropdownMenuBox(
                        expanded = expandedPets,
                        onExpandedChange = { expandedPets = !expandedPets }
                    ) {
                        OutlinedTextField(
                            value = selectedPet?.name ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = {
                                Text(
                                    "Selecciona una mascota",
                                    fontFamily = quicksandFont,
                                    color = TextGray
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPets)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            shape = RoundedCornerShape(12.dp),
                            placeholder = {
                                Text(
                                    "Selecciona una mascota",
                                    fontFamily = quicksandFont,
                                    color = Color.Gray
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryBlue,
                                unfocusedBorderColor = PrimaryBlue,
                                cursorColor = PrimaryBlue,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                fontFamily = quicksandFont,
                                fontSize = 16.sp,
                                color = TextGray
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedPets,
                            onDismissRequest = { expandedPets = false },
                            modifier = Modifier
                                .background(Color(0xFFF5F8FA), shape = RoundedCornerShape(12.dp))
                                .padding(vertical = 4.dp)
                        ) {
                            pets.forEach { pet ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = pet.name,
                                            fontFamily = quicksandFont,
                                            color = Color.Black,
                                            fontSize = 16.sp
                                        )
                                    },
                                    onClick = {
                                        viewModel.onPetSelected(pet)
                                        expandedPets = false

                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                        .background(Color.Transparent)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = service,
                            onValueChange = {},
                            readOnly = true,
                            label = {
                                Text(
                                    "Servicio",
                                    fontFamily = quicksandFont,
                                    color = TextGray
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            shape = RoundedCornerShape(12.dp),
                            placeholder = {
                                Text(
                                    "Selecciona un servicio",
                                    fontFamily = quicksandFont,
                                    color = Color.Gray
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryBlue,
                                unfocusedBorderColor = PrimaryBlue,
                                cursorColor = PrimaryBlue,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                fontFamily = quicksandFont,
                                fontSize = 16.sp,
                                color = TextGray
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .background(Color(0xFFF5F8FA), shape = RoundedCornerShape(12.dp))
                                .padding(vertical = 4.dp)
                        ) {
                            serviceOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = option,
                                            fontFamily = quicksandFont,
                                            color = Color.Black,
                                            fontSize = 16.sp
                                        )
                                    },
                                    onClick = {
                                        viewModel.onServiceChange(option)
                                        expanded = false
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                        .background(Color.Transparent)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = viewModel::onDescriptionChange,
                        placeholder = { Text("", fontFamily = quicksandFont) },
                        label = {
                            Text(
                                "Descripción general",
                                fontFamily = quicksandFont,
                                color = TextGray
                            )
                        },
                        singleLine = false,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
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

                    OutlinedTextField(
                        value = if (hasDateSelected) dateFormatter.format(date.time) else "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Fecha", fontFamily = quicksandFont, color = TextGray) },
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(
                                    Icons.Default.CalendarToday,
                                    contentDescription = "Fecha",
                                    tint = PrimaryBlue
                                )
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                "Selecciona una fecha",
                                fontFamily = quicksandFont,
                                color = Color.Gray
                            )
                        },
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

                    OutlinedTextField(
                        value = if (hasTimeSelected) timeFormatter.format(time.time) else "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Hora", fontFamily = quicksandFont, color = TextGray) },
                        trailingIcon = {
                            IconButton(onClick = { showTimePicker = true }) {
                                Icon(
                                    Icons.Default.AccessTime,
                                    contentDescription = "Hora",
                                    tint = PrimaryBlue
                                )
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                "Selecciona una hora",
                                fontFamily = quicksandFont,
                                color = Color.Gray
                            )
                        },
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

                    Spacer(modifier = Modifier.height(40.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CancelarCitaButton {
                            navController.navigate(NavRoutes.HomeClient.route) {
                                popUpTo(NavRoutes.RegisterAppointment.route) { inclusive = true }
                            }
                        }
                        GuardarCitaButton {
                            handleSave()
                        }
                    }

                    if (showDatePicker) {
                        val calendar = Calendar.getInstance()
                        DatePickerDialog(
                            context,
                            { _, year, month, day ->
                                calendar.set(year, month, day)
                                viewModel.onDateChange(calendar)
                                showDatePicker = false
                            },
                            date.get(Calendar.YEAR),
                            date.get(Calendar.MONTH),
                            date.get(Calendar.DAY_OF_MONTH)
                        ).apply {
                            setOnDismissListener { showDatePicker = false }
                            show()
                        }
                    }

                    if (showTimePicker) {
                        TimePickerDialog(
                            initialTime = time,
                            onTimeSelected = {
                                viewModel.onTimeChange(it)
                                showTimePicker = false
                            },
                            onDismiss = { showTimePicker = false }
                        )
                    }

                    ConfirmationDialog(
                        show = showSuccessDialog,
                        onDismiss = {
                            token?.let {
                                navController.navigate(NavRoutes.HomeClient.route) {
                                    popUpTo(NavRoutes.HomeClient.route) { inclusive = true }
                                }
                            } ?: run {
                                navController.navigate(NavRoutes.Login.route) {
                                    popUpTo(NavRoutes.Login.route) { inclusive = true }
                                }
                            }
                            showSuccessDialog = false
                            successTitle = ""
                            successMessage = ""
                        },
                        Title = successTitle,
                        Message = successMessage
                    )


                    ErrorDialog(
                        show = showErrorDialog,
                        onDismiss = {
                            showErrorDialog = false
                            errorTitle = ""
                            errorMessage = ""
                        },
                        title = errorTitle,
                        message = errorMessage
                    )

                }
            }
        }
    }
}
