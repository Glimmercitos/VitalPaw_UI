package me.vitalpaw.network.request

data class AppointmentUpdateRequest(
    val service: String,
    val description: String,
    val date: String,
    val time: String
)
