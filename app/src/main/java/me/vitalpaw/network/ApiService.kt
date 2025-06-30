package me.vitalpaw.network

import me.vitalpaw.models.Appointment
import me.vitalpaw.models.MedicalRecord
import me.vitalpaw.models.Pet
import me.vitalpaw.models.User
import me.vitalpaw.network.request.AppointmentUpdateRequest
import me.vitalpaw.network.request.CreateAppointmentRequest
import me.vitalpaw.network.request.MedicalRecordRequest
import me.vitalpaw.network.request.RegisterRequest
import me.vitalpaw.network.response.AppointmentUpdateResponse
import me.vitalpaw.network.response.LoginResponse
import me.vitalpaw.network.response.MedicalRecordResponse
import me.vitalpaw.network.response.MedicalRecordsResponse
import me.vitalpaw.network.response.RegisterResponse
import me.vitalpaw.network.response.VetAppointmentsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("api/users/register")
    suspend fun register(
        @Header("Authorization") token: String,
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    @POST("api/users/login")
    suspend fun login(
        @Header("Authorization") token: String
    ): Response<LoginResponse>

    @GET("api/users/getUser")
    suspend fun getUserData(
        @Header("Authorization") token: String
    ): Response<User>
    @GET("api/appointments/vet")
    suspend fun getVetAppointments(
        @Header("Authorization") token: String
    ): Response<VetAppointmentsResponse>

    @GET("api/appointments/vet/{id}")
    suspend fun getAppointmentById(
        @Header("Authorization") token: String,
        @Path("id") appointmentId: String
    ): Response<Appointment>
    @DELETE("api/appointments/vet/delete/{id}")
    suspend fun deleteAppointment(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<Unit>

    @POST("api/medicalRecords/{id}")
    suspend fun addMedicalRecord(
        @Header("Authorization") token: String,
        @Path("id") appointmentId: String,
        @Body request: MedicalRecordRequest
    ): Response<MedicalRecordResponse>

    @GET("api/medicalRecords/getPet/{petId}")
    suspend fun getMedicalRecordsByPetId(
        @Header("Authorization") token: String,
        @Path("petId") petId: String
    ): Response<MedicalRecordsResponse>

    @GET("api/medicalRecords/getPet/{petId}/{medicalRecordId}")
    suspend fun getMedicalRecordById(
        @Header("Authorization") token: String,
        @Path("petId") petId: String,
        @Path("medicalRecordId") medicalRecordId: String
    ): Response<MedicalRecordResponse>

    @PUT("api/appointments/vet/edit/{id}")
    suspend fun editAppointment(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: AppointmentUpdateRequest
    ): Response<AppointmentUpdateResponse>

    @POST("api/appointments/create")
    suspend fun createAppointment(
        @Header("Authorization") token: String,
        @Body request: CreateAppointmentRequest
    ): Response<AppointmentUpdateResponse>

    @GET("api/pets/my-pets")
    suspend fun getMyPets(
        @Header("Authorization") token: String
    ): Response<List<Pet>>

    @GET("api/appointments/my-appointments")
    suspend fun getMyAppointments(
        @Header("Authorization") token: String
    ): Response<List<Appointment>>


    @DELETE("api/appointments/client/delete/{id}")
    suspend fun deleteAppointmentClient(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<Unit>

}
