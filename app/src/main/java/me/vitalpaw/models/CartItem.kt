package me.vitalpaw.models

data class CartItem(
    val _id: String,
    val product: Product,
    val quantity: Int,
    val subtotal: Int
)
