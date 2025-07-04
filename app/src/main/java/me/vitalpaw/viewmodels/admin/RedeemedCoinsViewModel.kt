package me.vitalpaw.viewmodel.admin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.vitalpaw.network.response.RedeemedPurchaseResponse
import me.vitalpaw.repository.cliente.CartRepository
import javax.inject.Inject

@HiltViewModel
class RedeemedCoinsViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {

    private val _redeemedPurchases = MutableStateFlow<List<RedeemedPurchaseResponse>>(emptyList())
    val redeemedPurchases: StateFlow<List<RedeemedPurchaseResponse>> = _redeemedPurchases

    private val _totalRedeemedCoins = MutableStateFlow(0)
    val totalRedeemedCoins: StateFlow<Int> = _totalRedeemedCoins

    fun fetchRedeemedData(token: String) {
        viewModelScope.launch {
            val purchasesResponse = repository.getLastRedeemedPurchases(token)
            val totalResponse = repository.getTotalRedeemedPoints(token)

            if (purchasesResponse.isSuccessful) {
                _redeemedPurchases.value = purchasesResponse.body() ?: emptyList()
            } else {
                Log.e(
                    "RedeemedVM",
                    "❌ Error al obtener últimas compras: Código ${purchasesResponse.code()}, " +
                            "ErrorBody: ${purchasesResponse.errorBody()?.string()}"
                )
            }

            if (totalResponse.isSuccessful) {
                Log.d("RedeemedVM", "✅ Resumen recibido: ${totalResponse.body()}")
                _totalRedeemedCoins.value = totalResponse.body()?.totalRedeemedCoins ?: 0
            } else {
                Log.e(
                    "RedeemedVM",
                    "❌ Error al obtener total canjeado: Código ${totalResponse.code()}, " +
                            "ErrorBody: ${totalResponse.errorBody()?.string()}"
                )
            }
        }
    }
}
