package me.vitalpaw.viewmodels.shop

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.vitalpaw.models.Product
import me.vitalpaw.repository.cliente.ProductRepository
import javax.inject.Inject

@HiltViewModel
class HomeShopViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products


    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadCatalog(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getProducts(token)
                _products.value = result
                _error.value = null
                Log.d("ProductVW", "productos recibidos: ${result.size}")
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("ProductVM", "Error al cargar productos: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadProductById(token: String, id: String) {
        viewModelScope.launch {
            try {
                val result = repository.getProductById(token, id)
                _product.value = result
            } catch (e: Exception) {
                Log.e("ProductDetailVM", "Error al cargar producto: ${e.message}")
            }
        }
    }

}