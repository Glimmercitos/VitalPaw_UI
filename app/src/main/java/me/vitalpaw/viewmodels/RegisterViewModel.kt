package me.vitalpaw.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    var name by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var phone by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var showError by mutableStateOf(false)
        private set

    fun onNameChange(newName: String) {
        name = newName
        if (showError) showError = false
    }

    fun onEmailChange(newEmail: String) {
        email = newEmail
        if (showError) showError = false
    }

    fun onPhoneChange(newPhone: String) {
        phone = newPhone
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

    fun onRegisterClick() {
        showError = name.isBlank() || email.isBlank() || phone.isBlank() ||
                password.isBlank() || confirmPassword.isBlank() ||
                password != confirmPassword
    }

    var isPasswordVisible by mutableStateOf(false)
        private set

    fun onTogglePasswordVisibility(){
        isPasswordVisible = !isPasswordVisible
    }
}