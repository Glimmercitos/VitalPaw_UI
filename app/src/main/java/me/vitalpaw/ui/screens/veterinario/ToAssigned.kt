package me.vitalpaw.ui.screens.veterinario

import android.app.DatePickerDialog
import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
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
import me.vitalpaw.ui.components.buttons.CancelarCitaButton
import me.vitalpaw.ui.components.buttons.GuardarCitaButton
import me.vitalpaw.ui.components.icons.TimePickerDialog
import me.vitalpaw.ui.components.modal.ConfirmationDialog
import me.vitalpaw.ui.components.modal.ErrorDialog
import me.vitalpaw.ui.components.navigationBar.TopBarStatic
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.SessionViewModel
import me.vitalpaw.viewmodels.veterinario.ToAssignedViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

val PrimaryBlue = Color(0xFF3695B9)
val TextGray = Color(0xFF606060)

//@Preview(showBackground = true)
//@Composable
//fun PreviewToAssigned() {
//    ToAssigned(navController = NavHostController(LocalContext.current))
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToAssigned(
    navController: NavHostController,
    appointmentId: String,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    viewModel: ToAssignedViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val token by sessionViewModel.firebaseToken.collectAsState()
    val isLoading by sessionViewModel.isLoading.collectAsState()

    Log.d("ToAssigned", "Token actual: $token")

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

    val serviceOptions = listOf("grooming", "consulta médica", "emergencias")
    var expanded by remember { mutableStateOf(false) }

    val hasDateSelected = date.timeInMillis != 0L
    val hasTimeSelected = time.timeInMillis != 0L

    var errorTitle by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var successTitle by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    fun handleSave() {
        if (viewModel.description.value.isBlank() || viewModel.selectedService.value.isBlank() || !hasDateSelected || !hasTimeSelected) {
            errorTitle = "Error al asignar cita"
            errorMessage = "Campos vacíos"
            showErrorDialog = true
            return
        }

        viewModel.updateAppointment(appointmentId) {
            successTitle = "Cita guardada!"
            successMessage = "Cita asignada correctamente."
            showSuccessDialog = true

        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color(0xFF6980BF)
            )
        }
        return
    }

    Scaffold(
        topBar = {
            TopBarStatic(
                title = "ASIGNAR CITA",
                navController = navController,
                sessionViewModel = sessionViewModel
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = TextGray)
                    }
                    Text(
                        text = "ASIGNAR CITA",
                        fontFamily = quicksandFont,
                        fontSize = 20.sp,
                        color = TextGray,
                        fontWeight = FontWeight.Medium
                    )
                    IconButton(onClick = { /* Menú futuro */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú", tint = TextGray)
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
                    CancelarCitaButton { navController.popBackStack() }
                    GuardarCitaButton(onClick = { handleSave() })
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
                        navController.navigate(NavRoutes.Home.route) {
                            popUpTo(NavRoutes.Home.route) { inclusive = true }
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