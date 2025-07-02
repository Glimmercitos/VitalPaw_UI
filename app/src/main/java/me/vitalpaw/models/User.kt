package me.vitalpaw.models

data class User(
    val id: String? = null,
    val googleId: String,
    val name: String,
    val email: String,
    val role: String = "cliente", // cliente, veterinario, admin
    val createdAt: String? = null,
    val vitalCoins: Int = 500,
    val redeemedCoins: Int = 0
)