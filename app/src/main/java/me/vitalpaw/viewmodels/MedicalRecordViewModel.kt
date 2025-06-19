package me.vitalpaw.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.vitalpaw.models.MedicalRecord
import me.vitalpaw.repository.MedicalRecordRepository
import javax.inject.Inject

@HiltViewModel
class MedicalRecordViewModel @Inject constructor(
    private val repository: MedicalRecordRepository
) : ViewModel() {

    private val _record = MutableStateFlow<MedicalRecord?>(null)
    val record: StateFlow<MedicalRecord?> = _record

    fun loadRecordById(appointmentId: String) {
        _record.value = repository.getMedicalRecordById(appointmentId)
    }

    fun updateNotes(notes: String) {
        _record.value = _record.value?.copy(notes = notes)
    }

    fun updateTreatment(treatment: String) {
        _record.value = _record.value?.copy(treatment = treatment)
    }

    fun saveRecord() {
        println("Guardado localmente: ${_record.value}")
    }
}

