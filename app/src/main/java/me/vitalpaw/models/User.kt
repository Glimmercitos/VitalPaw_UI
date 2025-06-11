package me.vitalpaw.models

data class User(
    val id: String,
    val googleId: String,
    val name: String,
    val email: String,
    val avatar: String?,
    val role: String // cliente, veterinario, admin
)
