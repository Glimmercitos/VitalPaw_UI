package me.vitalpaw.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import me.vitalpaw.models.Pet
import me.vitalpaw.network.request.AppointmentUpdateRequest
import me.vitalpaw.network.request.CreateAppointmentRequest
import me.vitalpaw.repository.AppointmentRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@HiltViewModel
class ToAssignedViewModel @Inject constructor(
    private val repository: AppointmentRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _selectedService = MutableStateFlow("")
    val selectedService: StateFlow<String> = _selectedService

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _selectedDate = MutableStateFlow(Calendar.getInstance())
    val selectedDate: StateFlow<Calendar> = _selectedDate

    private val _selectedTime = MutableStateFlow(
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0) // ← hora 00
            set(Calendar.MINUTE, 0)      // ← minutos 00
        }
    )
    val selectedTime: StateFlow<Calendar> = _selectedTime

    private var _selectedPet = MutableStateFlow<Pet?>(null)
    var selectedPet: StateFlow<Pet?> = _selectedPet

    var error by mutableStateOf<String?>(null)
    var isSuccess by mutableStateOf<Boolean>(false)


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
    fun onPetSelected(pet: Pet) {
        _selectedPet.value = pet
    }

    fun updateAppointment(appointmentId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val user = auth.currentUser
                val token = user?.getIdToken(false)?.await()?.token
                if (token.isNullOrEmpty()) throw Exception("Token no válido")

                val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(_selectedDate.value.time)
                val timeStr = SimpleDateFormat("HH:mm", Locale.getDefault()).format(_selectedTime.value.time)

                val request = AppointmentUpdateRequest(
                    service = _selectedService.value,
                    description = _description.value,
                    date = dateStr,
                    time = timeStr
                )

                repository.editAppointment(token, appointmentId, request)
                onSuccess()
                isSuccess = true
                error = null
            } catch (e: Exception) {
                error = e.message
                Log.e("ToAssignedViewModel", "Error al asignar cita", e)
            }
        }
    }
    fun createAppointment(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val user = auth.currentUser
                val token = user?.getIdToken(false)?.await()?.token
                if (token.isNullOrEmpty()) throw Exception("Token no válido")

                val pet = _selectedPet.value ?: throw Exception("Debe seleccionar una mascota")

                val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(_selectedDate.value.time)
                val timeStr = SimpleDateFormat("HH:mm", Locale.getDefault()).format(_selectedTime.value.time)

                val request = CreateAppointmentRequest(
                    petId = pet.id,
                    service = _selectedService.value,
                    description = _description.value,
                    date = dateStr,
                    time = timeStr
                )

                repository.createAppointment(token, request)
                onSuccess()
                isSuccess = true
                error = null
            } catch (e: Exception) {
                error = e.message
                Log.e("ToAssignedViewModel", "Error al crear cita", e)
            }
        }
    }





}
