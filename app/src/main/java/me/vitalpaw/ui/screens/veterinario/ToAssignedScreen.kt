package me.vitalpaw.ui.screens.veterinario

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Calendar
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.*

@Composable
fun ToAssignedScreen(viewModel: ToAssignedViewModel = viewModel()) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            viewModel.selectedFecha = String.format("%02d/%02d/%02d", dayOfMonth, month + 1, year % 100)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Selector de servicio
            OutlinedTextField(
                value = viewModel.selectedServicio,
                onValueChange = { viewModel.selectedServicio = it },
                label = { Text("servicio") },
                trailingIcon = {
                    DropdownMenuIcon(
                        items = viewModel.servicios,
                        selectedItem = viewModel.selectedServicio,
                        onItemSelected = { viewModel.selectedServicio = it }
                    )
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Descripción
            OutlinedTextField(
                value = viewModel.descripcion,
                onValueChange = { viewModel.descripcion = it },
                label = { Text("descripcion general") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            // Fecha
            OutlinedTextField(
                value = viewModel.selectedFecha,
                onValueChange = {},
                label = { Text("fecha") },
                trailingIcon = {
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(Icons.Default.CalendarToday, contentDescription = "Seleccionar fecha")
                    }
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Hora
            OutlinedTextField(
                value = viewModel.selectedHora,
                onValueChange = { viewModel.selectedHora = it },
                label = { Text("hora") },
                trailingIcon = {
                    DropdownMenuIcon(
                        items = viewModel.horas,
                        selectedItem = viewModel.selectedHora,
                        onItemSelected = { viewModel.selectedHora = it }
                    )
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { viewModel.clearFields() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF7E8E99))
                ) {
                    Text("CANCELAR", color = Color.White)
                }

                Button(
                    onClick = { viewModel.showDialog = true },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF7E8E99))
                ) {
                    Text("GUARDAR", color = Color.White)
                }
            }
        }

        // Diálogo de confirmación
        if (viewModel.showDialog) {
            AlertDialog(
                onDismissRequest = { viewModel.showDialog = false },
                title = {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = Color(0xFF8B6EF6),
                        modifier = Modifier.size(48.dp)
                    )
                },
                text = {
                    Text(
                        "Cita asignada correctamente",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF8B6EF6),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                },
                confirmButton = {
                    Button(
                        onClick = { viewModel.showDialog = false },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF8B6EF6))
                    ) {
                        Text("OK", color = Color.White)
                    }
                },
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

private fun Unit.show() {
    TODO("Not yet implemented")
}

@Composable
fun DropdownMenuIcon(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Desplegar menú")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    onItemSelected(item)
                    expanded = false
                }) {
                    Text(text = item)
                }
            }
        }
    }
}
