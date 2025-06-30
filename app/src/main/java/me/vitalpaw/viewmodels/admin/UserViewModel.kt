package me.vitalpaw.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.vitalpaw.models.User
import me.vitalpaw.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    var searchQuery = mutableStateOf("")
    var filteredUsers = mutableStateListOf<User>()
    var selectedUser = mutableStateOf<User?>(null)

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query

        if (selectedUser.value != null) {
            selectedUser.value = null
        }

        val allUsers = userRepository.getAllUsers()
        val filtered = if (query.isBlank()) {
            emptyList()
        } else {
            allUsers.filter {
                it.email.contains(query.trim(), ignoreCase = true)
            }
        }

        filteredUsers.clear()
        filteredUsers.addAll(filtered)
    }

    fun getPendingRole(): String {
        return if (selectedUser.value?.role == "veterinario") "veterinario" else "cliente"
    }

    fun selectUser(user: User) {
        selectedUser.value = user
    }

    fun isVeterinarian(): Boolean {
        return selectedUser.value?.role == "veterinario"
    }

    fun toggleVeterinarianRole(context: Context) {
        selectedUser.value?.let { user ->
            val newRole = if (user.role == "veterinario") "cliente" else "veterinario"
            val updatedUser = user.copy(role = newRole)
            selectedUser.value = updatedUser

            Toast.makeText(
                context,
                "Actualizando Rol a $newRole",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun getUserById(id: String): User? {
        return userRepository.getAllUsers().find { it.id == id }
    }


    fun confirmRoleChange(context: Context) {
        selectedUser.value?.let { user ->
            val newRole = if (user.role == "veterinario") "cliente" else "veterinario"
            val updatedUser = user.copy(role = newRole)
            selectedUser.value = null
            searchQuery.value = ""
            filteredUsers.clear()

            Toast.makeText(
                context,
                "Rol de ${user.email} actualizado a $newRole",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
