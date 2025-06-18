package me.vitalpaw.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.vitalpaw.ui.state.SessionUiState
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val repository: SessionRepository
) : ViewModel() {

    var uiState by mutableStateOf(SessionUiState())
        private set

    fun onEmailChange(newEmail: String) {
        uiState = uiState.copy(email = newEmail, showError = false)
    }

    fun onPasswordChange(newPassword: String) {
        uiState = uiState.copy(password = newPassword, showError = false)
    }

    fun onTogglePasswordVisibility() {
        uiState = uiState.copy(isPasswordVisible = !uiState.isPasswordVisible)
    }

    fun onLoginClick() {
        val showError = uiState.email.isBlank() || uiState.password.isBlank()
        if (showError) {
            uiState = uiState.copy(showError = true)
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val success = repository.login(uiState.email, uiState.password)
            uiState = uiState.copy(
                isLoggedIn = success,
                isLoading = false,
                showError = !success
            )
        }
    }
}
