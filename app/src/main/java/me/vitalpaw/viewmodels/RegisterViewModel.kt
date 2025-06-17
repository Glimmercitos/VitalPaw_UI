package me.vitalpaw.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class RegisterViewModel : ViewModel() {

    var name by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var gender by mutableStateOf("")
        private set

    var isPasswordVisible by mutableStateOf(false)
        private set

    var showError by mutableStateOf(false)
        private set

    fun onNameChange(value: String) {
        name = value
    }

    fun onEmailChange(value: String) {
        email = value
    }

    fun onPasswordChange(value: String) {
        password = value
    }

    fun onConfirmPasswordChange(value: String) {
        confirmPassword = value
    }

    fun onGenderChange(value: String) {
        gender = value
    }

    fun onTogglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun onRegisterClick() {
        showError = name.isBlank() || email.isBlank() || password.isBlank() ||
                confirmPassword.isBlank() || password != confirmPassword || gender.isBlank()

        if (!showError) {
            // Aquí podrías llamar a tu lógica de registro o navegación
        }
    }
}
