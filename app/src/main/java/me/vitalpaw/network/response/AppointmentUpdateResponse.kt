package me.vitalpaw.network.response

import me.vitalpaw.models.Appointment

data class AppointmentUpdateResponse (
    val message: String,
    val appointment: Appointment
)
