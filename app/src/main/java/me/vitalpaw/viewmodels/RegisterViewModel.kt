package me.vitalpaw.viewmodels

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.vitalpaw.network.request.RegisterRequest
import me.vitalpaw.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    var name by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var gender by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var showError by mutableStateOf(false)
        private set

    var errorMsg by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var isPasswordVisible by mutableStateOf(false)
        private set

    var isRegisterSuccess by mutableStateOf(false)
        private set

    fun onNameChange(newName: String) {
        name = newName
        if (showError) showError = false
    }

    fun onEmailChange(newEmail: String) {
        email = newEmail
        if (showError) showError = false
    }

    fun onGenderChange(newGender: String) {
        gender = newGender
        if (showError) showError = false
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        if (showError) showError = false
    }

    fun onConfirmPasswordChange(newConfirm: String) {
        confirmPassword = newConfirm
        if (showError) showError = false
    }

    fun onTogglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun onRegisterClick() {
        if (name.isBlank() || email.isBlank() || gender.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            errorMsg = "Rellena todos los campos"
            showError = true
            return
        }

        if (password != confirmPassword) {
            errorMsg = "Las contraseñas no coinciden"
            showError = true
            return
        }

        viewModelScope.launch {
            isLoading = true
            try {
                val token = repository.registerUserFirebase(email, password)
                if (token != null) {
                    val response = repository.registerInBackend(token, RegisterRequest(name, email, gender))
                    if (response.isSuccessful) {
                        isRegisterSuccess = true
                        Log.d("Registro", "Registro exitoso: ${response.body()?.message}")
                    } else {
                        val errorText = response.errorBody()?.string()
                        errorMsg = "Error del servidor: ${response.message()}\n$errorText"
                        showError = true
                        Log.e("Registro", "Error backend: ${response.code()} $errorText")
                    }
                } else {
                    errorMsg = "Token de autenticación inválido"
                    showError = true
                }
            } catch (e: Exception) {
                errorMsg = e.message ?: "Error desconocido"
                showError = true
                Log.e("Registro", "Excepción: ", e)
            } finally {
                isLoading = false
            }
        }
    }
}
