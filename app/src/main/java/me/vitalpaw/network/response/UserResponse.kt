package me.vitalpaw.network.response

import me.vitalpaw.models.User

data class UserResponse(
    val message: String,
    val user: User
)
