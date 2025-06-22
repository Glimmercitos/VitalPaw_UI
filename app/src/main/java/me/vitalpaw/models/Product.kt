package me.vitalpaw.models

data class Product(
    val name: String,
    val price: Int,
    val imageResId: Int,
    var quantity: Int = 1
)
