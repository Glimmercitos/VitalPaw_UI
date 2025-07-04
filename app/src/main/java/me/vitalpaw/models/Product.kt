package me.vitalpaw.models

data class Product(
    val _id: String,
    val name: String,
    val description: String,
    val priceInVitalCoins: Int,
    val image: String? = null,
)
