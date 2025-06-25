package me.vitalpaw.viewmodels.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.vitalpaw.models.Product
import me.vitalpaw.repository.ProductRepository

class HomeShopViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        viewModelScope.launch {
            repository.getProducts().collect {
                _products.value = it
            }
        }
    }
}