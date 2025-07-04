package me.vitalpaw.network.response

import me.vitalpaw.models.Appointment

data class VetAppointmentsResponse(
    val message: String,
    val appointments: List<Appointment>
)
