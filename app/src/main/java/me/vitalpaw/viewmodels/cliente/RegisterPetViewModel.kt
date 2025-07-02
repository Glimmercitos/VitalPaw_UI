package me.vitalpaw.viewmodels.cliente

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import me.vitalpaw.repository.PetRepository
import me.vitalpaw.utils.FileUtil
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RegisterPetViewModel @Inject constructor(
    private val repository: PetRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _species = MutableStateFlow("")
    val species: StateFlow<String> = _species

    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> = _birthDate

    private val _gender = MutableStateFlow("")
    val gender: StateFlow<String> = _gender

    private val _breed = MutableStateFlow("")
    val breed: StateFlow<String> = _breed

    private val _weight = MutableStateFlow("")
    val weight: StateFlow<String> = _weight

    private val _unitAge = MutableStateFlow("")
    val unitAge: StateFlow<String> = _unitAge

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    private val _showSuccessDialog = MutableStateFlow(false)
    val showSuccessDialog: StateFlow<Boolean> = _showSuccessDialog

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting


    fun onNameChange(value: String) { _name.value = value }
    fun onSpeciesChange(value: String) { _species.value = value }
    fun onBirthDateChange(value: String) { _birthDate.value = value }
    fun onGenderChange(value: String) { _gender.value = value }
    fun onBreedChange(value: String) { _breed.value = value }
    fun onWeightChange(value: String) { _weight.value = value }
    fun onUnitAgeChange(value: String) { _unitAge.value = value }
    fun onImageChange(uri: Uri) { _imageUri.value = uri }

    fun showSuccessDialog() { _showSuccessDialog.value = true }
    fun dismissSuccessDialog() { _showSuccessDialog.value = false }

    fun createPet(context: Context, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            if (_isSubmitting.value) return@launch // evita doble clic

            _isSubmitting.value = true
            try {
                val user = auth.currentUser
                val token = user?.getIdToken(false)?.await()?.token
                if (token.isNullOrEmpty()) throw Exception("Token no válido")

                // Calcular edad
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val birthDateObj = dateFormat.parse(birthDate.value)
                    ?: throw Exception("Fecha de nacimiento inválida")

                val today = Calendar.getInstance()
                val birth = Calendar.getInstance().apply { time = birthDateObj }

                val diffYear = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR)
                val diffMonth = today.get(Calendar.MONTH) - birth.get(Calendar.MONTH)

                val age = if (diffYear == 0) diffMonth else diffYear
                val unitAgeFinal = if (diffYear == 0) "months" else "years"

                repository.createPet(
                    token = token,
                    name = name.value,
                    species = species.value,
                    breed = breed.value,
                    gender = gender.value == "Macho",
                    age = age,
                    weight = weight.value,
                    unitAge = unitAgeFinal,
                    imageUri = imageUri.value
                )

                _showSuccessDialog.value = true
                _errorMessage.value = null
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace() // Muestra el error real en la consola/logcat
                onError("Por favor, completa todos los campos obligatorios.")
            }
        }
    }

}