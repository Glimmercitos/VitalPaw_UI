package me.vitalpaw.viewmodels.cliente

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.vitalpaw.models.Appointment
import me.vitalpaw.repository.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAppointmentsPetViewModel @Inject constructor(
    private val repository: AppointmentRepository
) : ViewModel() {

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadMyAppointments(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getMyAppointments(token)
                _appointments.value = result
                _error.value = null
                Log.d("AppointmentVW", "Appoint recibidas: ${result.size}")
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("appointmentVM", "Error al cargar citas: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteAppointment(token: String, id: String) {
        viewModelScope.launch {
            try {
                repository.deleteClientAppointment(token, id)
                _appointments.update { it.filterNot { appt -> appt.id == id } }
            } catch (e: Exception) {
                Log.e("AppointmentVM", "Error al eliminar cita: ${e.message}")
                _error.value = e.message
            }
        }
    }

}
