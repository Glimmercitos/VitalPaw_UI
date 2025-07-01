package me.vitalpaw.network.request

data class AddToCartRequest(
    val productId: String,
    val quantity: Int
)
