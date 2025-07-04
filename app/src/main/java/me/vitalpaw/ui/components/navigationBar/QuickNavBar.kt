package me.vitalpaw.ui.components.navigationBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.SessionViewModel

@Composable
fun TopBarStatic(
    title: String,
    navController: NavController,
    sessionViewModel: SessionViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(50.dp)            )
            .padding(vertical = 20.dp, horizontal = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Home",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable {
                    navController.navigate(NavRoutes.Home.route) {
                        popUpTo(NavRoutes.Home.route) { inclusive = true }
                    }
                },
            tint = Color.DarkGray
        )

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = quicksandFont,
                color = Color.Gray,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.align(Alignment.Center)
        )

        Icon(
            imageVector = Icons.Default.Logout,
            contentDescription = "Logout",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable {
                    sessionViewModel.logout()
                    navController.navigate(NavRoutes.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
            tint = Color.DarkGray
        )
    }
}