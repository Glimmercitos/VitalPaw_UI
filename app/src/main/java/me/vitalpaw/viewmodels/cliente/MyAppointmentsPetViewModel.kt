//package me.vitalpaw.viewmodels.cliente
//
//import androidx.lifecycle.ViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import me.vitalpaw.models.Appointment
//import me.vitalpaw.repository.AppointmentRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject
//
//@HiltViewModel
//class MyAppointmentsPetViewModel @Inject constructor(
//    private val appointmentRepository: AppointmentRepository
//) : ViewModel() {
//
//    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
//    val appointments: StateFlow<List<Appointment>> = _appointments
//
//    init {
//        loadAppointments()
//    }
//
//    private fun loadAppointments() {
//        // Simulación de carga
//        val data = appointmentRepository.getAppointments()
//        _appointments.value = data
//    }
//}
