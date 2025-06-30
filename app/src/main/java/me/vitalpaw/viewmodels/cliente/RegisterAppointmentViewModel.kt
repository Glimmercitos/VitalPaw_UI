package me.vitalpaw.viewmodels.cliente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.vitalpaw.models.Pet
import me.vitalpaw.repository.PetRepository
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class RegisterAppointmentViewModel @Inject constructor(
    private val petRepository: PetRepository
) : ViewModel() {

    private val _selectedService = MutableStateFlow("Consulta")
    val selectedService: StateFlow<String> = _selectedService

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _selectedDate = MutableStateFlow(Calendar.getInstance())
    val selectedDate: StateFlow<Calendar> = _selectedDate

    private val _selectedTime = MutableStateFlow(Calendar.getInstance())
    val selectedTime: StateFlow<Calendar> = _selectedTime

    private val _petList = MutableStateFlow<List<Pet>>(emptyList())
    val petList: StateFlow<List<Pet>> = _petList

    private val _selectedPet = MutableStateFlow<Pet?>(null)
    val selectedPet: StateFlow<Pet?> = _selectedPet

    init {
        loadPets()
    }

    private fun loadPets() {
        viewModelScope.launch {
            _petList.value = petRepository.getPets()
        }
    }

    fun onPetSelected(pet: Pet) {
        _selectedPet.value = pet
    }

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
