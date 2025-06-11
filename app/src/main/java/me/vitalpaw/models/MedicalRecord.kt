package me.vitalpaw.models

data class MedicalRecord(
    val id: String,
    val notes: String,
    val treatment: String,
    val pet: Pet,
    val appointment: Appointment? = null,
    val service: String,
    val description: String,
    val date: String,
    val time: String
)
