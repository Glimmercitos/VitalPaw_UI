package me.vitalpaw.viewmodels.cliente

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

class RegisterAppointmentViewModel : ViewModel() {

    private val _selectedService = MutableStateFlow("Consulta")
    val selectedService: StateFlow<String> = _selectedService

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _selectedDate = MutableStateFlow(Calendar.getInstance())
    val selectedDate: StateFlow<Calendar> = _selectedDate

    private val _selectedTime = MutableStateFlow(Calendar.getInstance())
    val selectedTime: StateFlow<Calendar> = _selectedTime

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
}