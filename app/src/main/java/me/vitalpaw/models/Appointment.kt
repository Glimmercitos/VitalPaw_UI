package me.vitalpaw.models


data class Appointment(
    val id: String? = null,
    val owner: User,
    val pet: Pet,
    val service: String,
    val description: String,
    val date: String,
    val time: String,
    val veterinarian: User,
    val createdAt: String? = null
)
