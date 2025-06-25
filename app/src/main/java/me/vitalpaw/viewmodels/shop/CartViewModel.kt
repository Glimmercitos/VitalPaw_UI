package me.vitalpaw.viewmodels.shop

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.vitalpaw.models.Product

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<Pair<Product, Int>>>(emptyList())
    val cartItems: StateFlow<List<Pair<Product, Int>>> = _cartItems
    fun addToCart(product: Product, quantity: Int) {
        val current = _cartItems.value.toMutableList()
        current.add(Pair(product, quantity))
        _cartItems.value = current
    }

    fun removeFromCart(product: Product) {
        _cartItems.value = _cartItems.value.filterNot { it.first.name == product.name }
    }
    fun updateQuantity(product: Product, newQuantity: Int) {
        val current = _cartItems.value.toMutableList()
        val index = current.indexOfFirst { it.first.id == product.id }
        if (index != -1) {
            current[index] = product to newQuantity
            _cartItems.value = current
        }
    }
    fun clearCart() {
        _cartItems.value = emptyList()
    }

}