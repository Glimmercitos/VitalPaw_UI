package me.vitalpaw.network.response

data class CheckoutResponse(
    val message: String,
    val totalGastado: Int,
    val vitalCoinsRestantes: Int
)
