package me.vitalpaw.viewmodels.veterinario

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.vitalpaw.models.Appointment
import me.vitalpaw.models.MedicalRecord
import me.vitalpaw.repository.MedicalRecordRepository
import javax.inject.Inject

@HiltViewModel
class MedicalRecordViewModel @Inject constructor(
    val repository: MedicalRecordRepository
    ) : ViewModel() {

    private val _record = MutableStateFlow<MedicalRecord?>(null)
    val record: StateFlow<MedicalRecord?> = _record

    private val _showSuccessDialog = MutableStateFlow(false)
    val showSuccessDialog: StateFlow<Boolean> = _showSuccessDialog

    private val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog: StateFlow<Boolean> = _showErrorDialog

    private val _successTitle = MutableStateFlow("")
    val successTitle: StateFlow<String> = _successTitle

    private val _successMessage = MutableStateFlow("")
    val successMessage: StateFlow<String> = _successMessage

    private val _errorTitle = MutableStateFlow("")
    val errorTitle: StateFlow<String> = _errorTitle

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun getRecordsByPetId(petId: String): List<MedicalRecord> {
       return repository.getMedicalRecordsByPetId(petId)
    }

    fun loadRecordById(appointmentId: String) {
        _record.value = repository.getMedicalRecordById(appointmentId)
    }

//    fun getPetById(petId: String): Pet? {
//        return repository.getPetById(petId)
//    }

    fun updateNotes(notes: String) {
        _record.value = _record.value?.copy(notes = notes)
    }

    fun updateTreatment(treatment: String) {
        _record.value = _record.value?.copy(treatment = treatment)
    }

    fun saveRecord() {
        val current = _record.value
        if (current == null || current.notes.isBlank() || current.treatment.isBlank()) {
            _errorTitle.value = "Error al asignar cita"
            _errorMessage.value = "Campos vacíos"
            _showErrorDialog.value = true
            return
        }

        _successTitle.value = "Cita guardada!"
        _successMessage.value = "Cita asignada correctamente"
        _showSuccessDialog.value = true
    }
        fun dismissSuccessDialog() {
        _showSuccessDialog.value = false
        _successTitle.value = ""
        _successMessage.value = ""
    }

    fun dismissErrorDialog() {
        _showErrorDialog.value = false
        _errorTitle.value = ""
        _errorMessage.value = ""
    }
    fun createNewRecordFromAppointment(appointment: Appointment?) {
        if (appointment == null) return

        val newRecord = MedicalRecord(
            id = "temp-${appointment.id}",
            notes = "",
            treatment = "",
            pet = appointment.pet,
            appointment = appointment,
            service = appointment.service,
            description = appointment.description,
            date = appointment.date,
            time = appointment.time
        )
        _record.value = newRecord
    }


}


