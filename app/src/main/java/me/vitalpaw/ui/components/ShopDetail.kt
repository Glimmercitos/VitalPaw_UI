package me.vitalpaw.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.vitalpaw.ui.screens.cliente.PrimaryBlue
import me.vitalpaw.ui.screens.cliente.TextGray
import me.vitalpaw.ui.theme.quicksandFont

@Composable
fun ShopDetailBody(
    modifier: Modifier = Modifier,
    items: List<Pair<String, Int>>,
    total: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items.forEach { (name, points) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = name,
                            fontFamily = quicksandFont,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "$points pts",
                            fontFamily = quicksandFont,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Total de puntos:",
                fontFamily = quicksandFont,
                fontSize = 18.sp,
                color = PrimaryBlue
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "$total pts",
                fontFamily = quicksandFont,
                fontSize = 18.sp,
                color = TextGray
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShopDetailBodyPreview() {
    ShopDetailBody(
        items = listOf(
            "Arena para gato (2)" to 1600,
            "Juguete para ave (1)" to 125
        ),
        total = 1725
    )
}