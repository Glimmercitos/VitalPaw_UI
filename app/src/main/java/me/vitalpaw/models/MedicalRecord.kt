package me.vitalpaw.models

data class MedicalRecord(
    val _id: String? = null,
    val notes: String,
    val treatment: String,
    val pet: Pet,
    val appointment: String,
    val service: String,
    val description: String,
    val date: String,
    val time: String
)
