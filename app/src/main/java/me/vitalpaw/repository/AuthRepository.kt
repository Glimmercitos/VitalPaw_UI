package me.vitalpaw.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import me.vitalpaw.network.ApiService
import me.vitalpaw.network.request.RegisterRequest
import me.vitalpaw.network.response.LoginResponse
import me.vitalpaw.network.response.RegisterResponse


import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val auth: FirebaseAuth
) {
    suspend fun loginWithFirebase(email: String, password: String): String? {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user?.getIdToken(true)?.await()?.token
    }

    suspend fun loginInBackend(token: String): Response<LoginResponse> {
        return apiService.login("Bearer $token")
    }

    suspend fun registerUserFirebase(email: String, password: String): String? {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        return result.user?.getIdToken(true)?.await()?.token
    }

    suspend fun registerInBackend(
        token: String,
        request: RegisterRequest
    ): Response<RegisterResponse> {
        return apiService.register("Bearer $token", request)
    }
}
