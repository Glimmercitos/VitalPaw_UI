package me.vitalpaw.ui.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.vitalpaw.R
import me.vitalpaw.ui.theme.quicksandFont

@Composable
fun RedeemPurchaseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6699CC))
    ) {
        Icon(
            painter = painterResource(id = R.drawable.huellacoin),
            contentDescription = "Huella Coin",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = "CANJEAR",
            fontFamily = quicksandFont,
            fontWeight = FontWeight.Bold
        )
    }
}
