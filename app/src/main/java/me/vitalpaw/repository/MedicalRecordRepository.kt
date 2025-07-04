package me.vitalpaw.repository

import android.util.Log
import me.vitalpaw.models.Appointment
import me.vitalpaw.models.MedicalRecord
import me.vitalpaw.network.ApiService
import me.vitalpaw.network.request.MedicalRecordRequest
import me.vitalpaw.network.response.MedicalRecordResponse
import javax.inject.Inject

class MedicalRecordRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun saveRecord(token: String, appointmentId: String, request: MedicalRecordRequest): MedicalRecord {
        val response = apiService.addMedicalRecord("Bearer $token", appointmentId, request)
        if (response.isSuccessful) {
            return response.body()?.medicalRecord
                ?: throw Exception("Error: respuesta del servidor vacía")
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("MedicalRecordRepo", "Error al guardar expediente: ${response.code()} - $errorBody")
            throw Exception("Error ${response.code()}: ${errorBody ?: "Error desconocido"}")
        }
    }

    suspend fun getAppointmentById(token: String, appointmentId: String): Appointment {
        val response = apiService.getAppointmentById("Bearer $token", appointmentId)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Cita no encontrada")
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("MedicalRecordRepo", "Error al obtener cita: ${response.code()} - $errorBody")
            throw Exception("Error ${response.code()}: ${errorBody ?: "Error desconocido"}")
        }
    }

    suspend fun getRecordsByPetId(token: String, petId: String): List<MedicalRecord> {
        val response = apiService.getMedicalRecordsByPetId("Bearer $token", petId)
        if (response.isSuccessful) {
            return response.body()?.medicalRecords ?: emptyList()
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("MedicalRecordRepo", "Error al obtener historial: ${response.code()} - $errorBody")
            throw Exception("Error ${response.code()}: ${errorBody ?: "Error desconocido"}")
        }
    }

    suspend fun getMedicalRecordById(token: String, petId: String, medicalRecordId: String): MedicalRecord {
        val response = apiService.getMedicalRecordById("Bearer $token", petId, medicalRecordId)
        if (response.isSuccessful) {
            return response.body()?.medicalRecord ?: throw Exception("Registro médico no encontrado")
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("MedicalRecordRepo", "Error al obtener registro médico: ${response.code()} - $errorBody")
            throw Exception("Error ${response.code()}: ${errorBody ?: "Error desconocido"}")
        }
    }




}
