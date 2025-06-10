package me.vitalpaw.ui.screens.veterinario

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ToAssignedViewModel : ViewModel() {

    // Datos quemados
    val servicios = listOf("Grooming", "Consulta", "Vacunación")
    val horas = listOf("9:00", "10:30", "1:20", "3:00")

    // Estado
    var selectedServicio by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var selectedFecha by mutableStateOf("")
    var selectedHora by mutableStateOf("")
    var showDialog by mutableStateOf(false)

    fun clearFields() {
        selectedServicio = ""
        descripcion = ""
        selectedFecha = ""
        selectedHora = ""
    }
}
