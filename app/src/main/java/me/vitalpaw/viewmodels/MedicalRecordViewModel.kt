package me.vitalpaw.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.vitalpaw.models.Appointment
import me.vitalpaw.models.MedicalRecord
import me.vitalpaw.network.request.MedicalRecordRequest
import me.vitalpaw.repository.MedicalRecordRepository
import javax.inject.Inject

@HiltViewModel
class MedicalRecordViewModel @Inject constructor(
    private val repository: MedicalRecordRepository
) : ViewModel() {

    var appointment by mutableStateOf<Appointment?>(null)
        private set
    var medicalRecord by mutableStateOf<MedicalRecord?>(null)
        private set

    var recordsByPetId by mutableStateOf<List<MedicalRecord>>(emptyList())
        private set

    var notes by mutableStateOf("")
    var treatment by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun loadAppointment(token: String, appointmentId: String) {
        viewModelScope.launch {
            isLoading = true
            try {

                val result = repository.getAppointmentById(token, appointmentId)
                appointment = result
                error = null
            } catch (e: Exception) {
                error = e.message
                Log.e("MedicalRecordViewModel", "Error al cargar cita", e)
            } finally {
                isLoading = false
            }
        }
    }

    fun saveMedicalRecord(token: String, appointmentId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            try {
                val request = MedicalRecordRequest(notes, treatment)
                repository.saveRecord(token, appointmentId, request)
                onSuccess()
                error = null
            } catch (e: Exception) {
                error = e.message
                Log.e("MedicalRecordViewModel", "Error al guardar expediente médico", e)
            } finally {
                isLoading = false
            }
        }
    }

    fun loadRecordsByPetId(token: String, petId: String) {
        Log.d("MedicalRecordVM", "Cargando historial por petId=$petId y token=$token")
        viewModelScope.launch {
            isLoading = true
            try {
                val result = repository.getRecordsByPetId(token, petId)
                recordsByPetId = result
                error = null
                Log.d("MedicalRecordVM", "Historial cargado: $result")
            } catch (e: Exception) {
                recordsByPetId = emptyList()
                error = e.message
                Log.e("MedicalRecordViewModel", "Error al obtener historial por mascota", e)
            } finally {
                isLoading = false
            }
        }
    }

    fun loadMedicalRecord(token: String, medicalRecordId: String, petId: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                Log.d("MedicalRecordVM", "Cargando record con ID=$medicalRecordId y token=$token")

                val result = repository.getMedicalRecordById(token, petId, medicalRecordId)
                medicalRecord = result
                error = null
            } catch (e: Exception) {
                error = e.message ?: "Error desconocido"
                Log.e("MedicalRecordVM", "Error al cargar registro médico con ID=$medicalRecordId", e)
            } finally {
                isLoading = false
            }
        }
    }


}
