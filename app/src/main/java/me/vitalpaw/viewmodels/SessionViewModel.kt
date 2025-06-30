package me.vitalpaw.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _userRole = MutableStateFlow("")
    val userRole: StateFlow<String> = _userRole

    private val _showError = MutableStateFlow(false)
    val showError: StateFlow<Boolean> = _showError

    private val _errorMsg = MutableStateFlow("")
    val errorMsg: StateFlow<String> = _errorMsg

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible

    private val _isLoginSuccess = MutableStateFlow(false)
    val isLoginSuccess: StateFlow<Boolean> = _isLoginSuccess

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _firebaseToken = MutableStateFlow<String?>(null)
    val firebaseToken: StateFlow<String?> = _firebaseToken

    private val _bienvenidaError = MutableStateFlow<String?>(null)
    val bienvenidaError: StateFlow<String?> = _bienvenidaError

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        if (_showError.value) _showError.value = false
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        if (_showError.value) _showError.value = false
    }

    fun onTogglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun onLoginClick() {
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _errorMsg.value = "El correo o la contraseña están en blanco"
            _showError.value = true
            return
        }

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val token = authRepository.loginWithFirebase(_email.value, _password.value)
                _firebaseToken.value = token

                if (token != null) {
                    val response = authRepository.loginInBackend(token)
                    if (response.isSuccessful) {
                        _isLoginSuccess.value = true
                        _showError.value = false
                        _errorMsg.value = ""
                        loadUserData() // <- AÑADIR AQUÍ
                    } else {
                        _isLoginSuccess.value = false
                        _showError.value = true
                        _errorMsg.value = "Error en backend: ${response.message()}"
                    }
                } else {
                    _isLoginSuccess.value = false
                    _showError.value = true
                    _errorMsg.value = "No se pudo obtener token de Firebase"
                }
            } catch (e: HttpException) {
                _showError.value = true
                _errorMsg.value = "Error HTTP: ${e.message}"
                _isLoginSuccess.value = false
            } catch (e: Exception) {
                _showError.value = true
                _errorMsg.value = "Error: ${e.message}"
                _isLoginSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadUserData() {
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            _bienvenidaError.value = "Usuario no autenticado"
            return
        }

        viewModelScope.launch {
            try {
                val token = firebaseUser.getIdToken(true).await().token
                if (token != null) {
                    _firebaseToken.value = token
                    val response = userRepository.getUserData(token)
                    if (response.isSuccessful) {
                        _user.value = response.body()
                        _userRole.value = response.body()?.role ?: ""
                        _bienvenidaError.value = null
                    } else {
                        _bienvenidaError.value = "Error al obtener datos del usuario"
                    }
                } else {
                    _bienvenidaError.value = "Token inválido"
                }
            } catch (e: Exception) {
                _bienvenidaError.value = "Error: ${e.message}"
            }
        }
    }

    fun logout() {
        auth.signOut()
        _firebaseToken.value = null
        _user.value = null
        _email.value = ""
        _password.value = ""
        _userRole.value = ""
        _isLoginSuccess.value = false
        _showError.value = false
        _errorMsg.value = ""
        _isPasswordVisible.value = false
        _isLoading.value = false
        _bienvenidaError.value = null
    }

}
