package me.vitalpaw.models

data class User(
    val id: String? = null, // ID generado por MongoDB
    val googleId: String,
    val name: String,
    val email: String,
    val role: String = "cliente", // cliente, veterinario, admin
    val createdAt: String? = null
//    val updatedAt: String? = null
)