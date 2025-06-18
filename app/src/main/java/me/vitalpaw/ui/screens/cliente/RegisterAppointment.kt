package me.vitalpaw.ui.screens.cliente

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import me.vitalpaw.R
import me.vitalpaw.ui.components.buttons.CancelarCitaButton
import me.vitalpaw.ui.components.buttons.GuardarCitaButton
import me.vitalpaw.ui.components.modal.ConfirmationDialog
import me.vitalpaw.ui.components.modal.ErrorDialog
import me.vitalpaw.ui.components.icons.TimePickerDialog
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.ToAssignedViewModel
import java.text.SimpleDateFormat
import java.util.*

val PrimaryBlue = Color(0xFF6E7AE6)
val TextGray = Color(0xFF606060)
val ButtonBlue = Color(0xFF19486D)

@Preview(showBackground = true)
@Composable
fun PreviewRegisterAppointment() {
    RegisterAppointment(navController = NavHostController(LocalContext.current))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterAppointment(
    navController: NavHostController,
    viewModel: ToAssignedViewModel = viewModel()
) {
    val context = LocalContext.current

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

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Fondo",
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
                    text = "REGISTRAR CITA",
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
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    serviceOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, fontFamily = quicksandFont) },
                            onClick = {
                                viewModel.onServiceChange(option)
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = viewModel::onDescriptionChange,
                placeholder = {
                    Text("Ingrese una descripción", fontFamily = quicksandFont, color = Color.Gray)
                },
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
                value = dateFormatter.format(date.time),
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
                value = timeFormatter.format(time.time),
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
                CancelarCitaButton { showErrorDialog = true }
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

        ConfirmationDialog(show = showSuccessDialog) { showSuccessDialog = false }
        ErrorDialog(show = showErrorDialog) { showErrorDialog = false }
    }
}
