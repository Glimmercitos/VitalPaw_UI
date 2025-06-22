package me.vitalpaw.viewmodels

import androidx.lifecycle.ViewModel
import me.vitalpaw.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.vitalpaw.models.Product

class HomeShopViewModel : ViewModel() {

    private val _products = MutableStateFlow(
        listOf(
            Product("Shampoo para perro  435 ml", 300, R.drawable.prod7),
            Product("Comida para perro adulto", 1000, R.drawable.prod1),
            Product("Casa para gato", 2500, R.drawable.prod2),
            Product("Juguete para perro", 40, R.drawable.prod3),
            Product("Comida para gato Don Fino 454g", 40, R.drawable.prod4),
            Product("Arena para gato", 40, R.drawable.prod5),
            Product("Juguete para ave", 40, R.drawable.prod6),
            Product("Juguete para gato", 40, R.drawable.prod8)
        )
    )
    val products: StateFlow<List<Product>> = _products

    fun increment(index: Int) {
        val updated = _products.value.toMutableList()
        updated[index] = updated[index].copy(quantity = updated[index].quantity + 1)
        _products.value = updated
    }

    fun decrement(index: Int) {
        val updated = _products.value.toMutableList()
        if (updated[index].quantity > 1) {
            updated[index] = updated[index].copy(quantity = updated[index].quantity - 1)
            _products.value = updated
        }
    }
}
