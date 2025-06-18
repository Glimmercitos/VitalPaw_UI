package me.vitalpaw.models

data class User(
    val firebaseUid: String?,
    val email: String,
    val name: String,
    val gender: String,
    val role: String
)