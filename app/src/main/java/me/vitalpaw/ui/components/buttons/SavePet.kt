package me.vitalpaw.ui.components.buttons

import androidx.compose.runtime.Composable

@Composable
fun GuardarMascota(
    enabled: Boolean,
    onClick: () -> Unit
) {
    GuardarCitaButton(
        onClick = onClick
    )
}
