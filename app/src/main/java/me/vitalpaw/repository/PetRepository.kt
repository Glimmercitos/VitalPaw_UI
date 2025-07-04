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

import android.content.Context
import android.util.Log
import me.vitalpaw.models.Appointment
import me.vitalpaw.models.Pet
import me.vitalpaw.network.ApiService
import me.vitalpaw.network.request.CreateAppointmentRequest
import me.vitalpaw.network.request.CreatePetRequest
import javax.inject.Inject
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.vitalpaw.network.response.PetUpdateResponse
import me.vitalpaw.utils.toMultipartBody
import me.vitalpaw.utils.toRequestBody

class PetRepository @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
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


    suspend fun createPet(
        token: String,
        name: String,
        species: String,
        breed: String,
        gender: Boolean,
        age: Number,
        weight: String,
        unitAge: String,
        imageUri: Uri?
    ): PetUpdateResponse = withContext(Dispatchers.IO) {
        val namePart = name.toRequestBody()
        val speciesPart = species.toRequestBody()
        val breedPart = breed.toRequestBody()
        val genderPart = gender.toString().toRequestBody()
        val agePart = age.toString().toRequestBody()
        val weightPart = weight.toRequestBody()
        val unitAgePart = unitAge.toRequestBody()
        val imagePart = imageUri?.let { context.toMultipartBody(it, "image") }

        val response = apiService.createPet(
            token = "Bearer $token",
            name = namePart,
            species = speciesPart,
            breed = breedPart,
            gender = genderPart,
            age = agePart,
            weight = weightPart,
            unitAge = unitAgePart,
            image = imagePart
        )
        if (response.isSuccessful) {
            response.body()?: throw Exception("Respuesta vacía del servidor")
        } else {
            val errorBody = response.errorBody()?.string()
            throw Exception("Error al crear mascota: ${response.code()} - $errorBody")
        }
    }

    suspend fun deleteClientPet(token: String, id: String) {
        val response = apiService.deleteUserPet("Bearer $token", id)
        if (!response.isSuccessful) {
            // Loguear detalles del error HTTP
            val errorBody = response.errorBody()?.string() ?: "Error desconocido"
            Log.e("PetRepository", "Error HTTP al eliminar mascota: ${response.code()} - $errorBody")
            throw Exception("No se pudo eliminar la mascota: HTTP ${response.code()}")
        }

    }




}

