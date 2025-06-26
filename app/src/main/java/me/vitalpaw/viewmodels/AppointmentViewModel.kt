package me.vitalpaw.viewmodel

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


@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val repository: AppointmentRepository
) : ViewModel() {

    var appointments by mutableStateOf<List<Appointment>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun loadAppointments(token: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                val result = repository.getAppointments(token)
                Log.d("AppointmentViewModel", "Citas recibidas: ${result.size}")
                appointments = result
            } catch (e: Exception) {
                error = e.message
                Log.e("AppointmentViewModel", "Error al cargar citas: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }


    fun deleteAppointment(token: String, id: String) {
        viewModelScope.launch {
            try {
                repository.deleteAppointment(token, id)
                appointments = appointments.filterNot { it.id == id }
            } catch (e: Exception) {
                error = e.message
            }
        }
    }



}
