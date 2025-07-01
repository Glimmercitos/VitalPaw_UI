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
import androidx.compose.foundation.layout.width
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
import me.vitalpaw.ui.components.buttons.CancelarCitaButton
import me.vitalpaw.ui.components.buttons.GuardarCitaButton
import me.vitalpaw.ui.components.icons.TimePickerDialog
import me.vitalpaw.ui.components.modal.ConfirmationDialog
import me.vitalpaw.ui.components.modal.ErrorDialog
import me.vitalpaw.ui.components.topbar.TopBar
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.ToAssignedViewModel
import me.vitalpaw.viewmodels.cliente.RegisterAppointmentViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

val PrimaryBlue = Color(0xFF6E7AE6)
val TextGray = Color(0xFF606060)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterAppointment(
    navController: NavHostController,
    viewModel: ToAssignedViewModel = viewModel()
) {
    val context = LocalContext.current
    val viewModel: RegisterAppointmentViewModel = hiltViewModel()
    val pets by viewModel.petList.collectAsState()
    val selectedPet by viewModel.selectedPet.collectAsState()
    var expandedPets by remember { mutableStateOf(false) }
    val service by viewModel.selectedService.collectAsState()
    val description by viewModel.description.collectAsState()
    val date by viewModel.selectedDate.collectAsState()
    val time by viewModel.selectedTime.collectAsState()

    val dateFormatter = remember { SimpleDateFormat("dd/MM/yy", Locale.getDefault()) }
    val timeFormatter = remember { SimpleDateFormat("h:mm a", Locale.getDefault()) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    val serviceOptions = listOf("Emergencia", "Consulta", "Grooming")
    var expanded by remember { mutableStateOf(false) }

    val hasDateSelected = date.timeInMillis != 0L
    val hasTimeSelected = time.timeInMillis != 0L

    Box(modifier = Modifier.fillMaxSize()) {
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
            Spacer(modifier = Modifier.height(16.dp))
            TopBar(
                title = "REGISTRAR CITA",
                onBackClick = {
                    navController.navigate(NavRoutes.HomeClient.route) {
                        popUpTo(NavRoutes.RegisterAppointment.route) { inclusive = true }
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ExposedDropdownMenuBox(
                expanded = expandedPets,
                onExpandedChange = { expandedPets = !expandedPets }
            ) {
                OutlinedTextField(
                    value = selectedPet?.name ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Mascotas", fontFamily = quicksandFont, color = TextGray) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPets)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = {
                        Text("Selecciona una mascota", fontFamily = quicksandFont, color = Color.Gray)
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
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                ) {
                    pets.forEach { pet ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    pet.name,
                                    fontFamily = quicksandFont,
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                            },
                            onClick = {
                                viewModel.onPetSelected(pet)
                                expandedPets = false
                            }
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
                    label = { Text("Servicio", fontFamily = quicksandFont, color = TextGray) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = {
                        Text("Selecciona un servicio", fontFamily = quicksandFont, color = Color.Gray)
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
                label = { Text("Descripción general", fontFamily = quicksandFont, color = TextGray) },
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
                        Icon(Icons.Default.CalendarToday, contentDescription = "Fecha", tint = PrimaryBlue)
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Selecciona una fecha", fontFamily = quicksandFont, color = Color.Gray)
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
                        Icon(Icons.Default.AccessTime, contentDescription = "Hora", tint = PrimaryBlue)
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Selecciona una hora", fontFamily = quicksandFont, color = Color.Gray)
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
                    navController.navigate(NavRoutes.Home.route) {
                        popUpTo(NavRoutes.RegisterAppointment.route) { inclusive = true }
                    }
                }
                GuardarCitaButton { showSuccessDialog = true }
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
            onDismiss = { showSuccessDialog = false }
        )

        ErrorDialog(
            show = showErrorDialog,
            onDismiss = { showErrorDialog = false }
        )

    }
}
