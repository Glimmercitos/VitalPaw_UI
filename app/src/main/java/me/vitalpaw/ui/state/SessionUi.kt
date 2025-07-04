package me.vitalpaw.ui.state

data class SessionUiState(
    val email: String = "",
    val password: String = "",
    val showError: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false
)
