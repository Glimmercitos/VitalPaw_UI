package me.vitalpaw.ui.screens.veterinario

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Calendar
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.*

@Composable
fun ToAssignedScreen(viewModel: ToAssignedViewModel = viewModel()) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val servicios = listOf("Grooming", "Emergencia", "Consulta")
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("ASIGNAR CITA", style = MaterialTheme.typography.headlineSmall)

        // Dropdown servicio
        Box {
            OutlinedTextField(
                value = viewModel.servicioSeleccionado,
                onValueChange = {},
                label = { Text("Servicio") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                readOnly = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF3D5AFE),
                    unfocusedBorderColor = Color(0xFF3D5AFE),
                    textColor = Color.Black
                )
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                servicios.forEach { servicio ->
                    DropdownMenuItem(
                        text = { Text(servicio) },
                        onClick = {
                            viewModel.onServicioSeleccionado(servicio)
                            expanded = false
                        }
                    )
                }
            }
        }

        // Descripción
        OutlinedTextField(
            value = viewModel.descripcion,
            onValueChange = { viewModel.onDescripcionCambiada(it) },
            label = { Text("Descripción general") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF3D5AFE),
                unfocusedBorderColor = Color(0xFF3D5AFE),
                textColor = Color.Black
            )
        )

        // Fecha
        OutlinedTextField(
            value = viewModel.fecha,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    DatePickerDialog(context, { _, y, m, d ->
                        viewModel.onFechaSeleccionada("%02d/%02d/%02d".format(d, m + 1, y % 100))
                    }, year, month, day).show()
                },
            label = { Text("Fecha") },
            readOnly = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF3D5AFE),
                unfocusedBorderColor = Color(0xFF3D5AFE),
                textColor = Color.Black
            )
        )

        // Hora
        OutlinedTextField(
            value = viewModel.hora,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val hour = calendar.get(Calendar.HOUR_OF_DAY)
                    val minute = calendar.get(Calendar.MINUTE)
                    TimePickerDialog(context, { _: TimePicker, h, m ->
                        viewModel.onHoraSeleccionada("%02d:%02d".format(h, m))
                    }, hour, minute, true).show()
                },
            label = { Text("Hora") },
            readOnly = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF3D5AFE),
                unfocusedBorderColor = Color(0xFF3D5AFE),
                textColor = Color.Black
            )
        )

        // Botones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { viewModel.cancelar() },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF607D8B))
            ) {
                Text("CANCELAR")
            }

            Button(
                onClick = { viewModel.guardar() },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF607D8B))
            ) {
                Text("GUARDAR")
            }
        }
    }
}
