package me.vitalpaw.network.response

import me.vitalpaw.models.User

data class LoginResponse(
    val message: String,
    val user: User?
)