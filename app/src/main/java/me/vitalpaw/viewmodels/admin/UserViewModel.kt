package me.vitalpaw.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.vitalpaw.models.User
import me.vitalpaw.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var searchQuery by mutableStateOf("")
    var selectedUser by mutableStateOf<User?>(null)
    private val _filteredUsers = mutableStateListOf<User>()
    val filteredUsers: List<User> get() = _filteredUsers

    var showErrorDialog by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun onSearchQueryChanged(query: String, token: String) {
        searchQuery = query
        selectedUser = null
        if (query.isNotBlank()) {
            filterUsers(query, token)
        } else {
            _filteredUsers.clear()
        }
    }

    private fun filterUsers(query: String, token: String) {
        viewModelScope.launch {
            try {
                val results = userRepository.searchClients(token, query)
                _filteredUsers.clear()
                _filteredUsers.addAll(results)
            } catch (e: Exception) {
                errorMessage = "Error al buscar usuarios: ${e.message}"
                showErrorDialog = true
            }
        }
    }

    fun selectUser(user: User) {
        selectedUser = user
        searchQuery = user.email
        _filteredUsers.clear()
        Log.e("NUEVO USER", "$selectedUser")
    }

    fun getPendingRole(): String {
        return if (selectedUser?.role == "veterinario") "veterinario" else "cliente"
    }

    fun isVeterinarian(): Boolean {
        return selectedUser?.role == "veterinario"
    }

    fun changeUserRole(token: String, context: Context) {
        selectedUser?.let { user ->
            val newRole = getPendingRole()
            Log.i("UserViewModel", "Intentando cambiar rol de ${user.role} a $newRole para usuario: ${user.email}")
            val userId = user.id

            if (userId == null) {
                Log.e("UserViewModel", "ID del usuario es null. No se puede cambiar el rol.")
                return
            }

            viewModelScope.launch {
                try {
                    val response = userRepository.changeUserRole(token, userId, newRole)
                    if (response.isSuccessful) {
                        val updated = response.body()
                        updated?.let {
                            selectedUser = it
                            Toast.makeText(
                                context,
                                "Rol actualizado a $newRole",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.i(
                                "UserViewModel",
                                "Rol actualizado correctamente a $newRole para ${it.email}"
                            )
                        } ?: run {
                            Log.e("UserViewModel", "Respuesta sin cuerpo al actualizar rol.")
                            errorMessage = "Error: respuesta vacía del servidor"
                            showErrorDialog = true
                        }
                    } else {
                        val error = "Error HTTP ${response.code()}: ${response.message()}"
                        Log.e("UserViewModel", error)
                        errorMessage = error
                        showErrorDialog = true
                    }
                } catch (e: Exception) {
                    val error = "Excepción al cambiar el rol: ${e.message}"
                    Log.e("UserViewModel", error, e)
                    errorMessage = error
                    showErrorDialog = true
                }
            }
        } ?: run {
            Log.e("UserViewModel", "selectedUser es null. No se puede cambiar el rol.")
        }
    }

    suspend fun getUserById(userId: String, token: String): User? {
        return try {
            val response = userRepository.getUserById(userId, token)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("UserViewModel", "Error obteniendo usuario por id: ${response.code()} ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("UserViewModel", "Excepción obteniendo usuario por id", e)
            null
        }
    }

    fun clearSelectedUser() {
        selectedUser = null
    }



    fun dismissError() {
        showErrorDialog = false
        errorMessage = ""
    }

    fun reset() {
        searchQuery = ""
        selectedUser = null
        _filteredUsers.clear()
        dismissError()
    }
}