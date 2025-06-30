    package me.vitalpaw.viewmodel

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import dagger.hilt.android.lifecycle.HiltViewModel
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.launch
    import me.vitalpaw.models.Pet
    import me.vitalpaw.repository.PetRepository
    import javax.inject.Inject
    import android.util.Log

    @HiltViewModel
    class PetViewModel @Inject constructor(
        private val repository: PetRepository
    ) : ViewModel() {

        private val _pets = MutableStateFlow<List<Pet>>(emptyList())
        val pets: StateFlow<List<Pet>> = _pets

        private val _isLoading = MutableStateFlow(false)
        val isLoading: StateFlow<Boolean> = _isLoading

        private val _error = MutableStateFlow<String?>(null)
        val error: StateFlow<String?> = _error

        fun loadMyPets(token: String) {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val result = repository.getMyPets(token)
                    _pets.value = result
                    _error.value = null
                    Log.d("PetViewModel", "Mascotas recibidas: ${result.size}")
                } catch (e: Exception) {
                    _error.value = e.message
                    Log.e("PetViewModel", "Error al cargar mascotas: ${e.message}")
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }


