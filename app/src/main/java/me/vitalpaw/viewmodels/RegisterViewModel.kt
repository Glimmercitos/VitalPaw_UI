package me.vitalpaw.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.vitalpaw.network.request.RegisterRequest
import me.vitalpaw.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _gender = MutableStateFlow("")
    val gender: StateFlow<String> = _gender

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    private val _showError = MutableStateFlow(false)
    val showError: StateFlow<Boolean> = _showError

    private val _errorMsg = MutableStateFlow("")
    val errorMsg: StateFlow<String> = _errorMsg

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible

    private val _isRegisterSuccess = MutableStateFlow(false)
    val isRegisterSuccess: StateFlow<Boolean> = _isRegisterSuccess

    fun onNameChange(newName: String) {
        _name.value = newName
        if (_showError.value) _showError.value = false
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        if (_showError.value) _showError.value = false
    }

    fun onGenderChange(newGender: String) {
        _gender.value = newGender
        if (_showError.value) _showError.value = false
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        if (_showError.value) _showError.value = false
    }

    fun onConfirmPasswordChange(newConfirm: String) {
        _confirmPassword.value = newConfirm
        if (_showError.value) _showError.value = false
    }

    fun onTogglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun onRegisterClick() {
        if (_name.value.isBlank() || _email.value.isBlank() || _gender.value.isBlank() ||
            _password.value.isBlank() || _confirmPassword.value.isBlank()
        ) {
            _errorMsg.value = "Rellena todos los campos"
            _showError.value = true
            return
        }

        if (_password.value != _confirmPassword.value) {
            _errorMsg.value = "Las contraseñas no coinciden"
            _showError.value = true
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val token = repository.registerUserFirebase(_email.value, _password.value)
                if (token != null) {
                    val request = RegisterRequest(
                        name = _name.value,
                        email = _email.value,
                        gender = _gender.value
                    )
                    val response = repository.registerInBackend(token, request)
                    if (response.isSuccessful) {
                        _isRegisterSuccess.value = true
                        Log.d("Registro", "Registro exitoso: ${response.body()?.message}")
                    } else {
                        val errorText = response.errorBody()?.string()
                        _errorMsg.value = "Error del servidor: ${response.message()}\n$errorText"
                        _showError.value = true
                        Log.e("Registro", "Error backend: ${response.code()} $errorText")
                    }
                } else {
                    _errorMsg.value = "Token de autenticación inválido"
                    _showError.value = true
                }
            } catch (e: Exception) {
                _errorMsg.value = e.message ?: "Error desconocido"
                _showError.value = true
                Log.e("Registro", "Excepción: ", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetRegisterState() {
        _isRegisterSuccess.value = false
        _showError.value = false
        _errorMsg.value = ""
    }
}
