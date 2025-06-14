package me.vitalpaw.ui.screens.veterinario

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class ToAssignedViewModel : ViewModel() {
    var servicioSeleccionado by mutableStateOf("")
        private set

    var descripcion by mutableStateOf("")
        private set

    var fecha by mutableStateOf("")
        private set

    var hora by mutableStateOf("")
        private set

    fun onServicioSeleccionado(servicio: String) {
        servicioSeleccionado = servicio
    }

    fun onDescripcionCambiada(texto: String) {
        descripcion = texto
    }

    fun onFechaSeleccionada(nuevaFecha: String) {
        fecha = nuevaFecha
    }

    fun onHoraSeleccionada(nuevaHora: String) {
        hora = nuevaHora
    }

    fun cancelar() {
        servicioSeleccionado = ""
        descripcion = ""
        fecha = ""
        hora = ""
    }

    fun guardar() {
        // Aquí podrías agregar lógica para enviar o guardar la cita
    }
}
