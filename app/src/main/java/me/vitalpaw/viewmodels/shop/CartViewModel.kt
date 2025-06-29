package me.vitalpaw.viewmodels.shop

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.vitalpaw.models.Product

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {
    private val _cartItems = MutableStateFlow<List<Pair<Product, Int>>>(emptyList())
    val cartItems: StateFlow<List<Pair<Product, Int>>> = _cartItems

    fun addToCart(product: Product, quantity: Int) {
        val current = _cartItems.value.toMutableList()
        val existing = current.indexOfFirst { it.first.name == product.name }
        if (existing >= 0) {
            val updated = current[existing].copy(second = current[existing].second + quantity)
            current[existing] = updated
        } else {
            current.add(product to quantity)
        }
        _cartItems.value = current
    }

    fun updateQuantity(product: Product, newQuantity: Int) {
        _cartItems.value = _cartItems.value.map {
            if (it.first.name == product.name) product to newQuantity else it
        }
    }

    fun removeFromCart(product: Product) {
        _cartItems.value = _cartItems.value.filterNot { it.first.name == product.name }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}
