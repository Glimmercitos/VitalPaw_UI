package me.vitalpaw.repository

import me.vitalpaw.models.Appointment
import me.vitalpaw.network.ApiService
import me.vitalpaw.network.request.AppointmentUpdateRequest
import javax.inject.Inject
import android.util.Log
import me.vitalpaw.models.Pet
import me.vitalpaw.network.request.CreateAppointmentRequest


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


    suspend fun createAppointment(token: String, request: CreateAppointmentRequest): Appointment {
        val response = apiService.createAppointment("Bearer $token", request)
        if (response.isSuccessful) {
            return response.body()?.appointment ?: throw Exception("No se recibió la cita")
        } else {
            val errorBody = response.errorBody()?.string()
            throw Exception("Error al crear cita: ${response.code()} - $errorBody")
        }
    }

    suspend fun getMyAppointments(token: String): List<Appointment> {
        val response = apiService.getMyAppointments("Bearer $token")
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("Appointment", "Error HTTP: ${response.code()} - $errorBody")
            throw Exception("Error al obtener citas del usuario")
        }
    }

    suspend fun deleteClientAppointment(token: String, id: String) {
        val response = apiService.deleteAppointmentClient("Bearer $token", id)
        if (!response.isSuccessful) {
            // Loguear detalles del error HTTP
            val errorBody = response.errorBody()?.string() ?: "Error desconocido"
            Log.e("AppointmentRepository", "Error HTTP al eliminar cita: ${response.code()} - $errorBody")
            throw Exception("No se pudo eliminar la cita: HTTP ${response.code()}")
        }

    }

    suspend fun getAllAppointments(token: String): List<Appointment> {
        val response = apiService.getAllAppointments("Bearer $token")
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("AppointmentRepository", "Error HTTP: ${response.code()} - $errorBody")
            throw Exception("Error al obtener citas")
        }
    }
}