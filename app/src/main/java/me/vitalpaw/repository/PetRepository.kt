package me.vitalpaw.repository

import me.vitalpaw.models.Pet
import me.vitalpaw.repository.UserRepository
import me.vitalpaw.R
import javax.inject.Inject


class PetRepository @Inject constructor(
    private val userRepository: UserRepository)
{

    fun getPets(): List<Pet> {
        val owner = userRepository.getCurrentUser()
        return listOf(
            Pet(id = "p1", name = "Max", species = "Perro", age = 3, breed = "Labrador", weight = 25.0, gender = true,
                unitAge = "years", owner = owner, imageRes = R.drawable.dog1),
            Pet(id = "p2", name = "Lola", species = "Gato", age = 5, breed = "Siberiano", weight = 6.6, gender = false,
                unitAge = "years", owner = owner, imageRes = R.drawable.gato1),

        )
    }
}
