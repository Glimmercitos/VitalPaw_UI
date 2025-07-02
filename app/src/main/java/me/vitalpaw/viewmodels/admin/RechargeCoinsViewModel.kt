package me.vitalpaw.viewmodels.admin

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.vitalpaw.models.User
import me.vitalpaw.repository.UserRepository
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


@HiltViewModel
class RechargeCoinsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var emailQuery by mutableStateOf("")
    var selectedUser by mutableStateOf<User?>(null)
    var amount by mutableStateOf("")

    val confirmDialogTitle: String
        get() = "¿Hacer recarga a ${selectedUser?.name.orEmpty()}?"

    val confirmDialogMessage: String
        get() = "Se hará una recarga de $amount monedas"

    private val _filteredClients = mutableStateListOf<User>()

    val filteredClients: List<User> get() = _filteredClients

    var showConfirmDialog by mutableStateOf(false)
        private set

    var showErrorDialog by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf("")
        private set

    fun validateFields() {
        val isAmountValid = amount.toIntOrNull()?.let { it > 0 } == true
        if (selectedUser == null || amount.isBlank()) {
            errorMessage = "Debes completar todos los campos"
            showErrorDialog = true
        } else if (!isAmountValid) {
            errorMessage = "El monto debe ser un número entero positivo"
            showErrorDialog = true
        } else {
            showConfirmDialog = true
        }
    }

    fun dismissDialogs() {
        showErrorDialog = false
        showConfirmDialog = false
    }

//    init {
//        filterClients()
//    }

    fun onEmailChanged(newEmail: String, token: String) {
        emailQuery = newEmail
        selectedUser = null
        if (newEmail.isNotBlank()) {
            filterClients(newEmail, token)
        } else {
            _filteredClients.clear()
        }
    }


    fun selectUser(user: User) {
        selectedUser = user
        emailQuery = user.email
        _filteredClients.clear()
    }



    fun filterClients(query: String, token: String) {
        viewModelScope.launch {
            try {
                val results = userRepository.searchClients(token, query)
                _filteredClients.clear()
                _filteredClients.addAll(results)
            } catch (e: Exception) {
                errorMessage = "Error al buscar clientes: ${e.message}"
                showErrorDialog = true
            }
        }
    }

    fun postVitalCoins(token: String) {
        selectedUser?.id?.let { userId ->
            val coins = amount.toIntOrNull() ?: return
            viewModelScope.launch {
                try {
                    val response = userRepository.postVitalCoins(token, userId, coins)
                    if (response.isSuccessful) {
                        Log.d("Recarga", "Recarga exitosa para $userId con $coins monedas.")
                    } else {
                        Log.e("Recarga", "Error HTTP: ${response.code()} - ${response.message()}")
                        errorMessage = "No se pudo recargar monedas"
                        showErrorDialog = true
                    }
                } catch (e: Exception) {
                    Log.e("Recarga", "Error de red: ${e.message}", e)
                    errorMessage = "Error de red al recargar monedas"
                    showErrorDialog = true
                }
            }
        }
    }



    fun onAmountChanged(newAmount: String) {
        amount = newAmount
    }

    fun resetFields() {
        emailQuery = ""
        amount = ""
        selectedUser = null
        _filteredClients.clear()
    }

}
