package me.vitalpaw.viewmodels.cliente

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterPetViewModel : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _species = MutableStateFlow("")
    val species: StateFlow<String> = _species

    private val _age = MutableStateFlow("")
    val age: StateFlow<String> = _age

    private val _gender = MutableStateFlow("")
    val gender: StateFlow<String> = _gender

    private val _breed = MutableStateFlow("")
    val breed: StateFlow<String> = _breed

    private val _weight = MutableStateFlow("")
    val weight: StateFlow<String> = _weight

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    private val _showSuccessDialog = MutableStateFlow(false)
    val showSuccessDialog: StateFlow<Boolean> = _showSuccessDialog

    fun onNameChange(value: String) { _name.value = value }
    fun onSpeciesChange(value: String) { _species.value = value }
    fun onAgeChange(value: String) { _age.value = value }
    fun onGenderChange(value: String) { _gender.value = value }
    fun onBreedChange(value: String) { _breed.value = value }
    fun onWeightChange(value: String) { _weight.value = value }
    fun onImageChange(uri: Uri) { _imageUri.value = uri }

    fun showSuccessDialog() { _showSuccessDialog.value = true }
    fun dismissSuccessDialog() { _showSuccessDialog.value = false }
}