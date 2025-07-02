package me.vitalpaw.viewmodels.admin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.vitalpaw.models.User
import me.vitalpaw.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class VeterinarianViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _veterinarians = mutableStateOf<List<User>>(emptyList())
    val veterinarians: State<List<User>> = _veterinarians

    fun loadVeterinarians(token: String) {
        viewModelScope.launch {
            try {
                _veterinarians.value = userRepository.getVeterinarians(token)
            } catch (e: Exception) {
                println("Error al cargar veterinarios: ${e.message}")
                // Podrías exponer un estado de error si deseas mostrarlo en la UI
            }
        }
    }
}
