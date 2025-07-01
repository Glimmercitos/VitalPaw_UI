package me.vitalpaw.viewmodels.shop

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.vitalpaw.models.Product
import me.vitalpaw.network.response.CheckoutResponse
import me.vitalpaw.repository.cliente.CartRepository
import retrofit2.Response

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {
    private val _cartItems = MutableStateFlow<List<Pair<Product, Int>>>(emptyList())
    val cartItems: StateFlow<List<Pair<Product, Int>>> = _cartItems

    private val _cartMessage = MutableStateFlow<String?>(null)
    val cartMessage: StateFlow<String?> = _cartMessage

    fun addToCart(token: String, productId: String, quantity: Int) {
        viewModelScope.launch {
            try {
                val response = repository.addProductToCart(token, productId, quantity)
                if (response.isSuccessful) {
                    // Puedes emitir un mensaje o actualizar un estado si quieres mostrar algo en pantalla
                    Log.d("CartViewModel", "Producto agregado correctamente.")
                } else {
                    Log.e("CartViewModel", "Error en la respuesta: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error al agregar al carrito", e)
            }
        }
    }

    fun loadCart(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCart(token)
                if (response.isSuccessful) {
                    val items = response.body()?.cart?.mapNotNull {
                        if (it.product != null) {
                            Log.d("CartViewModel", "Producto cargado: ${it.product.name}")
                            it.product to it.quantity
                        } else {
                            Log.e("CartViewModel", "Producto nulo detectado en item: $it")
                            null
                        }
                    } ?: emptyList()

                    _cartItems.value = items

                } else {
                    Log.e("CartViewModel", "Error al cargar carrito: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error de red al cargar carrito", e)
            }
        }
    }



    fun updateQuantity(product: Product, newQuantity: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = repository.updateCartItem(token, product._id, newQuantity)
                if (response.isSuccessful) {
                    _cartItems.value = _cartItems.value.map {
                        if (it.first._id == product._id) product to newQuantity else it
                    }
                    Log.d("CartViewModel", "Cantidad actualizada correctamente.")
                } else {
                    Log.e("CartViewModel", "Error al actualizar cantidad: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error al actualizar cantidad", e)
            }
        }
    }


    fun removeFromCart(product: Product, token: String) {
        viewModelScope.launch {
            try {
                val response = repository.removeCartItem(token, product._id)
                if (response.isSuccessful) {
                    _cartItems.value = _cartItems.value.filterNot { it.first._id == product._id }
                    Log.d("CartViewModel", "Producto eliminado correctamente.")
                } else {
                    Log.e("CartViewModel", "Error al eliminar producto: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error al eliminar producto", e)
            }
        }
    }

    suspend fun checkout(token: String): Response<CheckoutResponse> {
        return repository.checkout(token)
    }


    fun clearCart() {
        _cartItems.value = emptyList()
    }
}
