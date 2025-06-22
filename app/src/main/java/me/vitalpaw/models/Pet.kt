package me.vitalpaw.models
import com.google.gson.annotations.SerializedName
data class Pet(
    @SerializedName("_id") val id: String,
    val name: String,
    val species: String,
    val age: Int,
    val breed: String,
    val weight: Double,
    val gender: Boolean, // true = macho, false = hembra
    val unitAge: String? = null, // "months" o "years"
    val owner: User,
    //val createdAt: String? = null,
    //val imageRes: Int
//    val updatedAt: String? = null
)
