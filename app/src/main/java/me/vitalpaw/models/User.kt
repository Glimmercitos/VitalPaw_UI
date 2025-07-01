package me.vitalpaw.models
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id") val id: String? = null,
    val firebaseUid: String?,
    val email: String,
    val name: String,
    val gender: String,
    val role: String,
    val vitalCoins: Int, // 👈 IMPORTANTE
)