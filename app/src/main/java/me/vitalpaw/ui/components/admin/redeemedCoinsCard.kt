    package me.vitalpaw.ui.components.admin

    import androidx.compose.foundation.BorderStroke
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.*
    import androidx.compose.material3.*
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.unit.dp
    import me.vitalpaw.models.User
    import androidx.compose.foundation.shape.RoundedCornerShape
    import me.vitalpaw.ui.theme.quicksandFont

    @Composable
    fun RedeemedCoinsCard(
        name: String,
        redeemedCoins: Int,
        modifier: Modifier = Modifier
    ) {
        Card(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(2.dp, Color(0xFF3695B9)),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Usuario: $name",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = quicksandFont
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Puntos canjeados: $redeemedCoins pts",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = quicksandFont
                )
            }
        }
    }
