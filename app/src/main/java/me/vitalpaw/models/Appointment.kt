package me.vitalpaw.models
import com.google.gson.annotations.SerializedName

data class Appointment(
    @SerializedName("_id") val id: String,
    val owner: User,
    val pet: Pet,
    @SerializedName("petName") val petName: String,
    val service: String,
    val description: String,
    val date: String,
    val time: String,
    val veterinarian: String // solo ID por ahora
)
