package me.vitalpaw.ui.components.navigationBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.vitalpaw.ui.theme.quicksandFont

@Composable
fun HomeTopBar(
    title: String,
    onMenuClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(50.dp))
            .padding(vertical = 10.dp, horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onHomeClick, modifier = Modifier.size(20.dp)) {
                Icon(Icons.Default.Home, contentDescription =  "Home" , tint = Color.Black,modifier = Modifier.size(50.dp),
                )
            }

            Text(
                text = title,
                fontSize = 17.sp,
                fontFamily = quicksandFont,
                color = Color.Gray
            )

            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
            }
        }
    }
}
