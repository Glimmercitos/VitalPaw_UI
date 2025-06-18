package me.vitalpaw.repository

import android.util.Log
import me.vitalpaw.network.ApiService
import me.vitalpaw.models.User
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getUserData(token: String): Response<User> {
        return try {
            apiService.getUserData("Bearer $token")
        } catch (e: Exception) {
            Log.e("UserRepository", "Error al obtener datos del usuario", e)
            Response.error(500, okhttp3.ResponseBody.create(null, "Error interno"))
        }
    }
}
