package me.vitalpaw.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


class SessionViewModel : ViewModel(){
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var showError by mutableStateOf(false)
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
        if (showError) showError = false
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        if (showError) showError = false
    }

    fun onLoginClick() {
        showError = email.isBlank() || password.isBlank()
        if (!showError) {
        }
    }

    var isPasswordVisible by mutableStateOf(false)
        private set

    fun onTogglePasswordVisibility(){
        isPasswordVisible = !isPasswordVisible
    }
}
