/*package me.vitalpaw.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import me.vitalpaw.models.Appointment
import me.vitalpaw.R


class AppointmentViewModel : ViewModel() {

    // Lista de citas (estado observable)
    var appointments by mutableStateOf<List<Appointment>>(emptyList())
        private set

    fun loadAppointments() {
        // Ejemplo de Lista con citas
        appointments = listOf(
            Appointment(1, R.drawable.dog1, "Max", "2025-06-08", "10:00 AM"),
            Appointment(2, R.drawable.gato1, "Luna", "2025-06-09", "11:30 AM"),
            Appointment(3, R.drawable.gato02, "Pepita", "2025-07-10", "1:30 PM"),
            Appointment(4, R.drawable.dog1, "Max", "2025-06-08", "10:00 AM"),
            Appointment(5, R.drawable.gato1, "Luna", "2025-06-09", "11:30 AM"),
            Appointment(6, R.drawable.gato02, "Pepita", "2025-07-10", "1:30 PM"),
            Appointment(7, R.drawable.dog1, "Max", "2025-06-08", "10:00 AM"),
            Appointment(8, R.drawable.gato02, "Pepita", "2025-07-10", "1:30 PM")


            )
    }

    // Para simular que no hay citas
    fun clearAppointments() {
        appointments = emptyList()
    }
}
*/