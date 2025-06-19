package me.vitalpaw.models

import android.R
import android.media.Image
data class Appointment(
    val id: String? = null,
    val owner: User,
    val pet: Pet,
    val petName: String,
    val service: String,
    val description: String,
    val date: String,
    val time: String,
    val veterinarian: User,
    val createdAt: String? = null
)
