package me.vitalpaw.viewmodels.veterinario

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.vitalpaw.models.Appointment
import me.vitalpaw.network.request.AppointmentUpdateRequest
import me.vitalpaw.repository.AppointmentRepository
import javax.inject.Inject
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val repository: AppointmentRepository
) : ViewModel() {

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    private val _allAppointments = MutableStateFlow<List<Appointment>>(emptyList())
    val allAppointments: StateFlow<List<Appointment>> = _allAppointments

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadAppointments(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getAppointments(token)
                Log.d("AppointmentViewModel", "Citas recibidas: ${result.size}")
                _appointments.value = result
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("AppointmentViewModel", "Error al cargar citas: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadAllAppointments(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getAllAppointments(token)
                _allAppointments.value = result
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


    fun markAppointmentAsComplete(token: String, id: String) {
        viewModelScope.launch {
            try {
                repository.deleteAppointment(token, id)
                _appointments.update { it.filterNot { appt -> appt.id == id } }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }



}
