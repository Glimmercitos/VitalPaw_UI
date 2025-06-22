package me.vitalpaw.repository

import me.vitalpaw.models.Appointment
import me.vitalpaw.network.ApiService
import me.vitalpaw.network.request.AppointmentUpdateRequest
import javax.inject.Inject
import android.util.Log


/*class AppointmentRepository @Inject constructor (
    private val userRepository: UserRepository,
    private val petRepository: PetRepository
) {

    fun getAppointments(): List<Appointment> {
        val owner = userRepository.getCurrentUser()
        val vet = userRepository.getVeterinarian()
        val pets = petRepository.getPets()

        return listOf(
            Appointment(
                id = "1",
                owner = owner,
                pet = pets[0].copy(),
                service = "Consulta médica",
                description = "Chequeo general",
                date = "2025-06-28",
                time = "10:00 AM",
                veterinarian = vet
            ),
            Appointment(
                id = "2",
                owner = owner,
                pet = pets[1].copy(),
                service = "Vacunación",
                description = "Primera dosis de vacunas",
                date = "2025-07-18",
                time = "1:00 PM",
                veterinarian = vet
            )
        )
    }

}*/
class AppointmentRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAppointments(token: String): List<Appointment> {
        val response = apiService.getVetAppointments("Bearer $token")
        if (response.isSuccessful) {
            return response.body()?.appointments ?: emptyList()
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("AppointmentRepository", "Error HTTP: ${response.code()} - $errorBody")
            throw Exception("Error al obtener citas")
        }
    }

    suspend fun deleteAppointment(token: String, id: String) {
        val response = apiService.deleteAppointment("Bearer $token", id)
        if (!response.isSuccessful) throw Exception("No se pudo eliminar la cita")
    }

    suspend fun editAppointment(token: String, id: String, data: AppointmentUpdateRequest) {
        val response = apiService.editAppointment("Bearer $token", id, data)
        if (!response.isSuccessful) throw Exception("No se pudo editar la cita")
    }
}

