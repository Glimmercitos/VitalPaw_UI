package me.vitalpaw.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import me.vitalpaw.R
import me.vitalpaw.ui.theme.quicksandFont

@Composable
fun ProductItem(
    image: String?,
    name: String,
    price: Int,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .width(260.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(12.dp),
                clip = false
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = image,  // URL de la imagen
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = name,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                fontFamily = quicksandFont
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$price PTS",
                fontWeight = FontWeight.Bold,
                fontFamily = quicksandFont,
                fontSize = 14.sp,
                color = Color(0xFF444444)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductItem() {
    ProductItem(
        image = "https://veterinariaelcountry.com/wp-content/uploads/Juguete-Para-Gato-Raton-De-Cuerda-11-Cm.jpg'",
        name = "Juguete para perro",
        price = 40
    )
}
