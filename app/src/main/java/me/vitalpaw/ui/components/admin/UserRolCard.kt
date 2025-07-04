package me.vitalpaw.ui.components.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.vitalpaw.models.User
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import me.vitalpaw.ui.theme.quicksandFont


@Composable
fun UserRolCard(
    user: User,
    onClick: () -> Unit,
    isSelected: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 10.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFFAFAFA) else Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = user.name, fontFamily = quicksandFont)
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = quicksandFont,
                    color = Color.Gray
                )
                Text(
                    text = "Rol: ${user.role}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = quicksandFont,
                    color = if (user.role == "veterinario") Color(0xFF4B877B) else Color(0xFF3695B9)
                )
            }
        }
    }
}

