package me.vitalpaw.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import me.vitalpaw.models.User
import me.vitalpaw.repository.AuthRepository
import me.vitalpaw.repository.UserRepository
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var showError by mutableStateOf(false)
        private set

    var errorMsg by mutableStateOf("")
        private set

    var isPasswordVisible by mutableStateOf(false)
        private set

    var isLoginSuccess by mutableStateOf(false)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var user by mutableStateOf<User?>(null)
        private set

    var bienvenidaError by mutableStateOf<String?>(null)
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
        if (showError) showError = false
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        if (showError) showError = false
    }

    fun onTogglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun onLoginClick() {
        if (email.isBlank() || password.isBlank()) {
            errorMsg = "El correo o la contraseña están en blanco"
            showError = true
            return
        }

        isLoading = true

        viewModelScope.launch {
            try {
                val token = authRepository.loginWithFirebase(email, password)
                if (token != null) {
                    val response = authRepository.loginInBackend(token)
                    if (response.isSuccessful) {
                        isLoginSuccess = true
                        showError = false
                        errorMsg = ""
                    } else {
                        isLoginSuccess = false
                        showError = true
                        errorMsg = "Error en backend: ${response.message()}"
                    }
                } else {
                    isLoginSuccess = false
                    showError = true
                    errorMsg = "No se pudo obtener token de Firebase"
                }
            } catch (e: HttpException) {
                showError = true
                errorMsg = "Error HTTP: ${e.message}"
                isLoginSuccess = false
            } catch (e: Exception) {
                showError = true
                errorMsg = "Error: ${e.message}"
                isLoginSuccess = false
            } finally {
                isLoading = false
            }
        }
    }

    fun loadUserData() {
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            bienvenidaError = "Usuario no autenticado"
            return
        }

        viewModelScope.launch {
            try {
                val token = firebaseUser.getIdToken(true).await().token
                if (token != null) {
                    val response = userRepository.getUserData(token)
                    if (response.isSuccessful) {
                        user = response.body()
                        bienvenidaError = null
                    } else {
                        bienvenidaError = "Error al obtener datos del usuario"
                    }
                } else {
                    bienvenidaError = "Token inválido"
                }
            } catch (e: Exception) {
                bienvenidaError = "Error: ${e.message}"
            }
        }
    }

    fun logout() {
        auth.signOut()
    }
}
