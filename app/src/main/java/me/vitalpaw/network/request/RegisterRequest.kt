package me.vitalpaw.network.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val phone: String,
)
