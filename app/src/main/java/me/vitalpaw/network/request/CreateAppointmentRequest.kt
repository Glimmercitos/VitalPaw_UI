package me.vitalpaw.network.request

data class CreateAppointmentRequest(
    val petId: String,
    val service: String,
    val description: String,
    val date: String,
    val time: String
)
