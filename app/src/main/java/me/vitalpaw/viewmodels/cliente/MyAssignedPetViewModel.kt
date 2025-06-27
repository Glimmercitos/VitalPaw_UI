package me.vitalpaw.viewmodels.cliente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.vitalpaw.models.Pet
import me.vitalpaw.repository.MyPetAssignedRepository

@HiltViewModel
class MyPetAssignedViewModel @Inject constructor(
    private val repository: MyPetAssignedRepository
) : ViewModel() {

    private val _assignedPets = MutableStateFlow<List<Pet>>(emptyList())
    val assignedPets: StateFlow<List<Pet>> = _assignedPets

    fun loadAssignedPets() {
        viewModelScope.launch {
            _assignedPets.value = repository.getAssignedPets()
        }
    }

    fun deleteAssignedPet(petId: String) {
        // Actualizar localmente para UI instantánea
        _assignedPets.value = _assignedPets.value.filter { it.id != petId }

        viewModelScope.launch {
            repository.deleteAssignedPet(petId)
            // No es necesario recargar la lista porque ya actualizamos localmente
        }
    }
}
