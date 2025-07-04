package me.vitalpaw.ui.components.navigationBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.vitalpaw.R
import me.vitalpaw.models.DrawerItem
import me.vitalpaw.ui.theme.quicksandFont

@Composable
fun SideMenuDrawer(
    items: List<DrawerItem>,
    onItemClick: (DrawerItem) -> Unit,
    modifier: Modifier = Modifier
) {

    val filteredItems = items.filter { it.route != "logout" }
    val logoutItem = items.find { it.route == "logout" }

    Column(
        modifier = modifier
            .background(Color(0xFFF5F5F5))
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Image (
            painter = painterResource( id  = R.drawable.logo),
            contentDescription = "Logo VitalPaw",
            modifier = Modifier
                .width(200.dp)
                .height(100.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            filteredItems.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(item) }
                        .padding(vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = Color(0xFF19486D)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = item.label,
                        fontSize = 18.sp,
                        color = Color(0xFF19486D),
                        fontFamily = quicksandFont
                    )
                }
            }
        }

        logoutItem?.let { item ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(item) }
                    .padding(vertical = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = Color(0xFF19486D)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = item.label,
                        fontSize = 18.sp,
                        color = Color(0xFF19486D),
                        fontFamily = quicksandFont
                    )
                }
            }
        }
    }
}
