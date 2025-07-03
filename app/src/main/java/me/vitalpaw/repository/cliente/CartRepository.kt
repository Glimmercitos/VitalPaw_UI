package me.vitalpaw.repository.cliente

import android.util.Log
import retrofit2.Response
import me.vitalpaw.network.ApiService
import me.vitalpaw.network.request.AddToCartRequest
import me.vitalpaw.network.request.UpdateCartItemRequest
import me.vitalpaw.network.response.CartResponse
import me.vitalpaw.network.response.CheckoutResponse
import me.vitalpaw.network.response.RedeemedPurchaseResponse
import me.vitalpaw.network.response.RedeemedSummaryResponse
import javax.inject.Inject

class CartRepository  @Inject constructor
    (private val apiService: ApiService) {

    suspend fun addProductToCart(token: String, productId: String, quantity: Int): Response<Unit> {
        val request = AddToCartRequest(productId, quantity)
        return apiService.addToCart("Bearer $token", request)
    }

    suspend fun getCart(token: String): Response<CartResponse> {
        return try {
            apiService.getCart("Bearer $token")
        } catch (e: Exception) {
            Log.e("CartRepository", "Error al obtener carrito", e)
            Response.error(500, okhttp3.ResponseBody.create(null, "Error interno"))
        }
    }

    suspend fun updateCartItem(token: String, productId: String, quantity: Int): Response<Unit> {
        val request = UpdateCartItemRequest(quantity)
        return apiService.updateCart("Bearer $token", productId, request)
    }

    suspend fun removeCartItem(token: String, productId: String): Response<Unit> {
        return apiService.removeCartItem("Bearer $token", productId)
    }

    suspend fun checkout(token: String): Response<CheckoutResponse> {
        val response = apiService.checkout("Bearer $token")
        if (response.isSuccessful) {
            Log.d("CartRepository", "✅ Checkout exitoso: ${response.body()}")
        } else {
            Log.e("CartRepository", "❌ Checkout fallo: Código ${response.code()}, ErrorBody: ${response.errorBody()?.string()}")
        }
        return response
    }

    suspend fun getLastRedeemedPurchases(token: String, limit: Int = 5): Response<List<RedeemedPurchaseResponse>> {
        return try {
            apiService.getRedeemedPurchases("Bearer $token", limit)
        } catch (e: Exception) {
            Log.e("CartRepository", "Error al obtener últimas compras", e)
            Response.error(500, okhttp3.ResponseBody.create(null, "Error interno"))
        }
    }

    suspend fun getTotalRedeemedPoints(token: String): Response<RedeemedSummaryResponse> {
        return try {
            apiService.getRedeemedSummary("Bearer $token")
        } catch (e: Exception) {
            Log.e("CartRepository", "Error al obtener resumen canjeado", e)
            Response.error(500, okhttp3.ResponseBody.create(null, "Error interno"))
        }
    }
}