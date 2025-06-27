package me.vitalpaw.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _appointment = MutableStateFlow<Appointment?>(null)
    val appointment: StateFlow<Appointment?> = _appointment

    private val _medicalRecord = MutableStateFlow<MedicalRecord?>(null)
    val medicalRecord: StateFlow<MedicalRecord?> = _medicalRecord

    private val _recordsByPetId = MutableStateFlow<List<MedicalRecord>>(emptyList())
    val recordsByPetId: StateFlow<List<MedicalRecord>> = _recordsByPetId

    private val _notes = MutableStateFlow("")
    val notes: StateFlow<String> = _notes

    private val _treatment = MutableStateFlow("")
    val treatment: StateFlow<String> = _treatment

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun onNotesChange(value: String) {
        _notes.value = value
    }

    fun onTreatmentChange(value: String) {
        _treatment.value = value
    }

    fun clearError() {
        _error.value = null
    }

    fun loadAppointment(token: String, appointmentId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getAppointmentById(token, appointmentId)
                _appointment.value = result
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("MedicalRecordViewModel", "Error al cargar cita", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveMedicalRecord(token: String, appointmentId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = MedicalRecordRequest(_notes.value, _treatment.value)
                repository.saveRecord(token, appointmentId, request)
                _error.value = null
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("MedicalRecordViewModel", "Error al guardar expediente médico", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadRecordsByPetId(token: String, petId: String) {
        Log.d("MedicalRecordVM", "Cargando historial por petId=$petId y token=$token")
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getRecordsByPetId(token, petId)
                _recordsByPetId.value = result
                _error.value = null
                Log.d("MedicalRecordVM", "Historial cargado: $result")
            } catch (e: Exception) {
                _recordsByPetId.value = emptyList()
                _error.value = e.message
                Log.e("MedicalRecordViewModel", "Error al obtener historial por mascota", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadMedicalRecord(token: String, medicalRecordId: String, petId: String) {
        Log.d("MedicalRecordViewModel", "Cargando record con ID: $medicalRecordId y petId: $petId")
        viewModelScope.launch {
            _isLoading.value = true
            try {

                val result = repository.getMedicalRecordById(token, petId, medicalRecordId)
                _medicalRecord.value = result
                Log.d("MedicalRecordViewModel", "Record recibido: $result")
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
                Log.e("MedicalRecordVM", "Error al cargar registro médico con ID=$medicalRecordId", e)
            } finally {
                _isLoading.value = false
            }
        }
    }


}
