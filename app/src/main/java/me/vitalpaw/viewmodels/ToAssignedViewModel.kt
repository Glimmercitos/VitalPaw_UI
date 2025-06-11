package me.vitalpaw.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import me.vitalpaw.models.Pet
import me.vitalpaw.models.Service

class ToAssignedViewModel : ViewModel() {
    val pets = mutableStateListOf(
        Pet(1, "Luna", "Perro", "Labrador", 3, "Carlos"),
        Pet(2, "Michi", "Gato", "Siames", 2, "Ana"),
        Pet(3, "Rocky", "Perro", "Pitbull", 4, "Luis")
    )

    val services = mutableStateListOf(
        Service(1, "Luna", "Consulta"),
        Service(2, "Michi", "Vacunación"),
        Service(3, "Rocky", "Grooming")
    )

    val selectedPet = mutableStateOf<Pet?>(null)
    val selectedService = mutableStateOf<Service?>(null)
    val description = mutableStateOf("")
    val date = mutableStateOf("")
    val hour = mutableStateOf("")
    val showDialog = mutableStateOf(false)
}
