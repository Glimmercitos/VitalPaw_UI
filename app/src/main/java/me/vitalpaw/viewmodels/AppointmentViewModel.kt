package me.vitalpaw.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import dagger.hilt.android.lifecycle.HiltViewModel
import me.vitalpaw.models.Appointment
import me.vitalpaw.repository.AppointmentRepository
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val repository: AppointmentRepository
) : ViewModel() {

    val appointments = mutableStateListOf<Appointment>()

    init {
        loadAppointments()
    }

    fun getAppointmentById(appointmentId: String): Appointment? {
        return appointments.find { it.id == appointmentId }
    }


    fun loadAppointments() {
        appointments.clear()
        appointments.addAll(repository.getAppointments())
    }

    fun getAppointmentByPetId(petId: String): List<Appointment>{
        return appointments.filter { it.pet.id == petId }
    }

    fun markAppointmentAsComplete(appointmentId: String){

    }
}
