package me.vitalpaw.network.response

import me.vitalpaw.models.CartItem

data class CartResponse(
    val vitalCoins: Int,
    val cart: List<CartItem>
)