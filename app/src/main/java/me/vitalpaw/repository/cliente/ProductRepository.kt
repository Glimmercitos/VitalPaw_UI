package me.vitalpaw.repository.cliente

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import me.vitalpaw.R
import me.vitalpaw.models.Appointment
import me.vitalpaw.models.Product
import me.vitalpaw.network.ApiService
import javax.inject.Inject

class ProductRepository @Inject constructor
    (private val apiService: ApiService){

    suspend fun getProducts(token: String): List<Product> {
        val response = apiService.getCatalog("Bearer $token")
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("Catalog", "Error HTTP: ${response.code()} - $errorBody")
            throw Exception("Error al obtener catalogo del usuario")
        }
    }
    suspend fun getProductById(token: String, productId: String): Product {
        val response = apiService.getProductById("Bearer $token", productId)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Producto no encontrado")
        } else {
            throw Exception("Error al obtener producto: ${response.code()}")
        }
    }
}