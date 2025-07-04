package me.vitalpaw.ui.components.buttons

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import me.vitalpaw.ui.theme.quicksandFont

@Composable
fun RedeemPurchaseButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // espacio externo opcional
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF19486D)),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .width(120.dp)
                .height(40.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 2.dp
            )
        ) {
            Text(
                text = "CANJEAR",
                fontFamily = quicksandFont,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
