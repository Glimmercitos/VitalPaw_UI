package me.vitalpaw.models

import com.google.gson.annotations.SerializedName

data class MedicalRecord(
    @SerializedName("_id") val id: String,
    val notes: String? = null,
    val treatment: String? = null,
    val pet: Pet,
    val appointment: String,
    val service: String? = null,
    val description: String? = null,
    val date: String,
    val time: String
)
