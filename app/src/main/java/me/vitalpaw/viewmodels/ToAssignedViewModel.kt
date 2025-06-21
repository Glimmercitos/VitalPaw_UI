package me.vitalpaw.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

class ToAssignedViewModel : ViewModel() {

    private val _selectedService = MutableStateFlow("")
    val selectedService: StateFlow<String> = _selectedService

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _selectedDate = MutableStateFlow(Calendar.getInstance())
    val selectedDate: StateFlow<Calendar> = _selectedDate

    private val _selectedTime = MutableStateFlow(
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
        }
    )
    val selectedTime: StateFlow<Calendar> = _selectedTime

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

    fun onServiceChange(service: String) {
        _selectedService.value = service
    }

    fun onDescriptionChange(desc: String) {
        _description.value = desc
    }

    fun onDateChange(date: Calendar) {
        _selectedDate.value = date
    }

    fun onTimeChange(time: Calendar) {
        _selectedTime.value = time
    }

    fun validateAndSave() {
        if (_selectedService.value.isBlank() ||
            _description.value.isBlank() ||
            _selectedDate.value.timeInMillis == 0L ||
            _selectedTime.value.timeInMillis == 0L
        ) {
            setError(
                title = "Error al asignar cita",
                message = "Completa todos los campos."
            )
        } else {
            setSuccess(
                title = "Cita asignada",
                message = "La cita ha sido asignada correctamente"
            )
        }
    }

    private fun setSuccess(title: String, message: String) {
        _successTitle.value = title
        _successMessage.value = message
        _showSuccessDialog.value = true
    }

    private fun setError(title: String, message: String) {
        _errorTitle.value = title
        _errorMessage.value = message
        _showErrorDialog.value = true
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
}


