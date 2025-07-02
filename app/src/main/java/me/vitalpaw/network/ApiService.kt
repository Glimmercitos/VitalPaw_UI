package me.vitalpaw.network

import com.tuapp.network.request.UpdateVitalCoinsRequest
import me.vitalpaw.models.Appointment
import me.vitalpaw.models.MedicalRecord
import me.vitalpaw.models.Pet
import me.vitalpaw.models.Product
import me.vitalpaw.models.User
import me.vitalpaw.network.request.AddToCartRequest
import me.vitalpaw.network.request.AppointmentUpdateRequest
import me.vitalpaw.network.request.CreateAppointmentRequest
import me.vitalpaw.network.request.CreatePetRequest
import me.vitalpaw.network.request.MedicalRecordRequest
import me.vitalpaw.network.request.RegisterRequest
import me.vitalpaw.network.request.RoleUpdateRequest
import me.vitalpaw.network.request.UpdateCartItemRequest
import me.vitalpaw.network.response.AppointmentUpdateResponse
import me.vitalpaw.network.response.CartResponse
import me.vitalpaw.network.response.CheckoutResponse
import me.vitalpaw.network.response.LoginResponse
import me.vitalpaw.network.response.MedicalRecordResponse
import me.vitalpaw.network.response.MedicalRecordsResponse
import me.vitalpaw.network.response.PetUpdateResponse
import me.vitalpaw.network.response.RegisterResponse
import me.vitalpaw.network.response.UserResponse
import me.vitalpaw.network.response.VetAppointmentsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("api/shop/catalog")
    suspend fun getCatalog(
        @Header("Authorization") token: String
    ): Response<List<Product>>

    @GET("api/shop/catalog/{id}")
    suspend fun getProductById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<Product>

    @POST("api/shop/cart")
    suspend fun addToCart(
        @Header("Authorization") token: String,
        @Body body:AddToCartRequest
    ): Response<Unit>

    @GET("api/shop/cart")
    suspend fun getCart(
        @Header("Authorization") token: String
    ): Response<CartResponse>

    @PUT("api/shop/cart/{productId}")
    suspend fun updateCart(
        @Header("Authorization") token: String,
        @Path("productId") id: String,
        @Body request: UpdateCartItemRequest
    ): Response<Unit>

    @DELETE("api/shop/cart/{productId}")
    suspend fun removeCartItem(
        @Header("Authorization") token: String,
        @Path("productId") productId: String
    ): Response<Unit>

    @POST("api/shop/checkout")
    suspend fun checkout(
        @Header("Authorization") token: String
    ): Response<CheckoutResponse>


    @Multipart
    @POST("api/pets/create")
    suspend fun createPet(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("species") species: RequestBody,
        @Part("breed") breed: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("age") age: RequestBody,
        @Part("weight") weight: RequestBody,
        @Part("unitAge") unitAge: RequestBody,
        @Part image: MultipartBody.Part? = null
    ): Response<PetUpdateResponse>

    @DELETE("api/pets/delete/user/{id}")
    suspend fun deleteUserPet(
        @Header("Authorization") token: String,
        @Path("id") petId: String
    ): Response<Unit>

    @POST("api/users/vitalCoins/{id}")
    suspend fun postVitalCoins(
        @Header("Authorization") token: String,
        @Path("id") userId: String,
        @Body request: UpdateVitalCoinsRequest
    ): Response<UserResponse>

    @GET("api/users/search-clients")
    suspend fun searchClients(
        @Header("Authorization") token: String,
        @Query("email") email: String
    ): Response<List<User>>

    @PUT("api/users/role/{id}")
    suspend fun changeRole(
        @Header("Authorization") token: String,
        @Path("id") userId: String,
        @Body request: RoleUpdateRequest
    ): Response<User>

}
