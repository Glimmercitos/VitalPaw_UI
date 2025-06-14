package me.vitalpaw.models

data class Appointment(
    val id: String? = null,
    val owner: User,
    val pet: Pet,
    val petName: String,
    val service: String, // "grooming", "consulta médica", "emergencias"
    val description: String,
    val date: String, // ISO 8601 format: "2025-06-14"
    val time: String, // e.g., "14:30"
    val veterinarian: User,
    val createdAt: String? = null
//    val updatedAt: String? = null
)