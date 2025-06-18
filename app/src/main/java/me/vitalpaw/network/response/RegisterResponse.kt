package me.vitalpaw.network.response

import me.vitalpaw.models.User

data class RegisterResponse(
    val message: String,
    val user: User?
)

