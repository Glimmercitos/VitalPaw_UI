package me.vitalpaw.ui.components.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.vitalpaw.R
import me.vitalpaw.models.User

@Composable
fun VeterinarianCard(
    user: User,
    onEditClick: () -> Unit)
{
    Card(
        modifier = Modifier
            .width(340.dp)
            .padding(8.dp)
            .height(100.dp),
        shape = RoundedCornerShape(55.dp),  // en 55 no 50 para evitar desbordamiento de la card
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row( //0xFF3695B9
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.White)
                .border(2.dp, Color(0xFF3695B9), CircleShape)
                .height(110.dp)
            ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(85.dp)
                    .background(Color.White, CircleShape)
                    .padding(2.dp)
                    .clip(CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.defaultuserimage),
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 15.dp)
                    .padding(5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF3C3B3B)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "editar veterinario",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFFA4232D),
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.clickable { onEditClick() }
                )
            }
        }
    }

}