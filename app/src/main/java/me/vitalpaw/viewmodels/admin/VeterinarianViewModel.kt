package me.vitalpaw.viewmodels.admin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.vitalpaw.models.User
import me.vitalpaw.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class VeterinarianViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _veterinarians = mutableStateOf<List<User>>(emptyList())
    val veterinarians: State<List<User>> = _veterinarians

    suspend fun loadVeterinarians() {
        _veterinarians.value = userRepository.getAllUsers()
            .filter { it.role == "veterinario" }
    }
}
