package me.vitalpaw.models

data class Product(
    val id: Int,
    val name: String,
    val points: Int,
    val imageResId: Int,
    val description: String = "Sin descripción"
)
