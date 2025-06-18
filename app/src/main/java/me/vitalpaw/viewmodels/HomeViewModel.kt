package me.vitalpaw.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val _promotions = MutableStateFlow(
        listOf(
            "promo1", "promo2", "promo3", "promo4"
        )
    )
    val promotions: StateFlow<List<String>> = _promotions
}
