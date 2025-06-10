package me.vitalpaw.models

data class Appointment(
    val id: Int,
    val imageRes: Int,
    val petName: String,
    val date: String,
    val time: String
)