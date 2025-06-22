package me.vitalpaw.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.vitalpaw.R
import me.vitalpaw.ui.components.buttons.GuardarCitaButton

@Composable
fun ProductItem(
    imageResId: Int,
    name: String,
    price: Int,
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { onDecrement() }) {
                    Icon(Icons.Default.Remove, contentDescription = "Restar")
                }
                Text(text = quantity.toString(), fontSize = 16.sp)
                IconButton(onClick = { onIncrement() }) {
                    Icon(Icons.Default.Add, contentDescription = "Sumar")
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$price PTS",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductItem() {
    ProductItem(
        imageResId = R.drawable.prod3, // Usa una imagen válida que tengas en drawable
        name = "Juguete para perro",
        price = 40,
        quantity = 1,
        onIncrement = {},
        onDecrement = {}
    )
}
