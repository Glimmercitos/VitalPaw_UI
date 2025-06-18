package me.vitalpaw.network

import me.vitalpaw.models.User
import me.vitalpaw.network.request.RegisterRequest
import me.vitalpaw.network.response.LoginResponse
import me.vitalpaw.network.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

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
}
