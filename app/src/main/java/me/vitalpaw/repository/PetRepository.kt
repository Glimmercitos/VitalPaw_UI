//package me.vitalpaw.repository
//
//import me.vitalpaw.models.Pet
//import me.vitalpaw.repository.UserRepository
//import me.vitalpaw.R
//import javax.inject.Inject


/*class PetRepository @Inject constructor(
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

*/
package me.vitalpaw.repository

import android.util.Log
import me.vitalpaw.models.Pet
import me.vitalpaw.network.ApiService
import javax.inject.Inject

class PetRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getMyPets(token: String): List<Pet> {
        val response = apiService.getMyPets("Bearer $token")
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("PetRepository", "Error HTTP: ${response.code()} - $errorBody")
            throw Exception("Error al obtener mascotas del usuario")
        }
    }

}

