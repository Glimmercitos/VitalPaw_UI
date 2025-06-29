package me.vitalpaw.repository.cliente

import me.vitalpaw.R
import me.vitalpaw.models.Pet
import me.vitalpaw.models.User
import me.vitalpaw.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPetAssignedRepository @Inject constructor(
    private val userRepository: UserRepository
) {

    private val assignedPets = mutableListOf<Pet>()

    init {
        val currentUser: User = userRepository.getCurrentUser()
        assignedPets.addAll(
            listOf(
                Pet(
                    id = "p1",
                    name = "Max",
                    species = "Perro",
                    age = 3,
                    breed = "Labrador",
                    weight = 25.0,
                    gender = true,
                    unitAge = "años",
                    owner = currentUser,
                    createdAt = "2024-01-01",
                    imageRes = R.drawable.dog1
                ),
                Pet(
                    id = "p2",
                    name = "Lola",
                    species = "Gato",
                    age = 5,
                    breed = "Siberiano",
                    weight = 6.6,
                    gender = false,
                    unitAge = "años",
                    owner = currentUser,
                    createdAt = "2024-01-02",
                    imageRes = R.drawable.gato1
                )
            )
        )
    }

    fun getAssignedPets(): List<Pet> {
        // Devuelve copia nueva para detectar cambios en StateFlow
        return assignedPets.toList()
    }

    fun deleteAssignedPet(petId: String) {
        assignedPets.removeIf { it.id == petId }
    }
}