package me.vitalpaw.ui.screens.shop

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import me.vitalpaw.ui.theme.quicksandFont
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "CARRITO",
                        fontSize = 20.sp,
                        fontFamily = quicksandFont,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) {
        Text(
            text = "Aquí irá el contenido del carrito",
            modifier = Modifier.padding(it),
            fontFamily = quicksandFont
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun CartScreenPreview() {
    CartScreen()
}
