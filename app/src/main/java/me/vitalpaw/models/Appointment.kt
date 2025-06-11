package me.vitalpaw.models

data class Appointment(
    val id: String,
    val owner: User,
    val pet: Pet,
    val petName: String,
    val service: String, // grooming, consulta médica, emergencias
    val description: String,
    val date: String, // ISO 8601 string (ej. "2025-06-15")
    val time: String,
    val veterinarian: User
)
