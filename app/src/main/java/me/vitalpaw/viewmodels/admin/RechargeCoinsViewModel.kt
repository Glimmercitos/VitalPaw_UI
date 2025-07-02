//package me.vitalpaw.viewmodels.admin
//
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import dagger.hilt.android.lifecycle.HiltViewModel
//import me.vitalpaw.models.User
//import me.vitalpaw.repository.UserRepository
//import javax.inject.Inject
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.setValue
//
//
//@HiltViewModel
//class RechargeCoinsViewModel @Inject constructor(
//    private val userRepository: UserRepository
//) : ViewModel() {
//
//    var emailQuery by mutableStateOf("")
//    var selectedUser by mutableStateOf<User?>(null)
//    var amount by mutableStateOf("")
//
//    val confirmDialogTitle: String
//        get() = "¿Hacer recarga a ${selectedUser?.name.orEmpty()}?"
//
//    val confirmDialogMessage: String
//        get() = "Se hará una recarga de $amount monedas"
//
//    private val _filteredClients = mutableStateListOf<User>()
//
//    val filteredClients: List<User> get() = _filteredClients
//
//    var showConfirmDialog by mutableStateOf(false)
//        private set
//
//    var showErrorDialog by mutableStateOf(false)
//        private set
//
//    var errorMessage by mutableStateOf("")
//        private set
//
//    fun validateFields() {
//        val isAmountValid = amount.toIntOrNull()?.let { it > 0 } == true
//        if (selectedUser == null || amount.isBlank()) {
//            errorMessage = "Debes completar todos los campos"
//            showErrorDialog = true
//        } else if (!isAmountValid) {
//            errorMessage = "El monto debe ser un número entero positivo"
//            showErrorDialog = true
//        } else {
//            showConfirmDialog = true
//        }
//    }
//
//    fun dismissDialogs() {
//        showErrorDialog = false
//        showConfirmDialog = false
//    }
//
//    init {
//        filterClients()
//    }
//
//    fun onEmailChanged(newEmail: String) {
//        emailQuery = newEmail
//        selectedUser = null
//        filterClients()
//    }
//
//    fun selectUser(user: User) {
//        selectedUser = user
//        emailQuery = user.email
//        _filteredClients.clear()
//    }
//
//    private fun filterClients() {
//        val query = emailQuery.trim().lowercase()
//        val clients = userRepository.getAllUsers()
//            .filter { it.role == "cliente" && it.email.contains(query, ignoreCase = true) }
//        _filteredClients.clear()
//        _filteredClients.addAll(clients)
//    }
//
//    fun onAmountChanged(newAmount: String) {
//        amount = newAmount
//    }
//
//    fun resetFields() {
//        emailQuery = ""
//        amount = ""
//        selectedUser = null
//        _filteredClients.clear()
//    }
//
//}
