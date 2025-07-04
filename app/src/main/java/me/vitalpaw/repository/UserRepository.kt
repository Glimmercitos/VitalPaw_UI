package me.vitalpaw.repository

import android.util.Log
import com.tuapp.network.request.UpdateVitalCoinsRequest
import me.vitalpaw.network.ApiService
import me.vitalpaw.models.User
import me.vitalpaw.network.request.RoleUpdateRequest
import me.vitalpaw.network.response.UserResponse
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

    suspend fun postVitalCoins(
        token: String,
        userId: String,
        coins: Int
    ): Response<UserResponse> {
        return try {
            apiService.postVitalCoins(
                token = "Bearer $token",
                userId = userId,
                request = UpdateVitalCoinsRequest(vitalCoins = coins)
            )
        } catch (e: Exception) {
            Log.e("UserRepository", "Error al postear VitalCoins para userId $userId: ${e.message}", e)
            Response.error(500, okhttp3.ResponseBody.create(null, "Error interno"))
        }
    }
    suspend fun searchClients(token: String, email: String): List<User> {
        return try {
            val response = apiService.searchClients("Bearer $token", email)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("UserRepository", "Error en búsqueda: ${response.code()} ${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Excepción en búsqueda: ${e.message}")
            emptyList()
        }
    }
    suspend fun changeUserRole(token: String, userId: String, newRole: String): Response<User> {
        val request = RoleUpdateRequest(role = newRole)
        return try {
            apiService.changeRole(
                token = "Bearer $token",
                userId = userId,
                request = request
            )
        } catch (e: Exception) {
            Log.e("UserRepository", "Error cambiando rol: ${e.message}", e)
            throw e
        }
    }
    suspend fun getVeterinarians(token: String): List<User> {
        val response = apiService.getVets("Bearer $token")
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error del servidor: ${response.code()} ${response.message()}")
        }
    }

    suspend fun getUserById(userId: String, token: String): Response<User> {
        return try {
            apiService.getUserById("Bearer $token", userId)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error obteniendo usuario por id: ${e.message}", e)
            Response.error(500, okhttp3.ResponseBody.create(null, "Error interno"))
        }
    }


}
