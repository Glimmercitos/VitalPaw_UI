package me.vitalpaw.ui.screens.administrador

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vitalpaw.ui.components.admin.RedeemedCoinsCard
import me.vitalpaw.viewmodel.UserViewModel
import me.vitalpaw.ui.components.buttons.SalirButton
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun RedeemedCoinsScreen(
    navController: NavController,
    viewModel: UserViewModel = hiltViewModel()
) {
    val redeemedClients = viewModel.userRepository.getAllUsers()
        .filter { it.role == "cliente" && it.redeemedCoins > 0 }

    val totalRedeemed = redeemedClients.sumOf { it.redeemedCoins }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp)
        ) {
            if (redeemedClients.isNotEmpty()) {
                Text(
                    text = "Total canjeado: ",
                    fontSize = 35.sp,
                    color = Color(0xFF3695B9),
                    textAlign = TextAlign.Center,
                    fontFamily = quicksandFont,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )

                Spacer(Modifier.height(15.dp))

                Text(
                    text = "$totalRedeemed pts",
                    fontSize = 35.sp,
                    color = Color(0xFF3695B9),
                    textAlign = TextAlign.Center,
                    fontFamily = quicksandFont,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(7.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (redeemedClients.isEmpty()) {
                    Text(
                        "Ningún cliente ha canjeado puntos todavía.",
                        textAlign = TextAlign.Center
                    )
                } else {
                    redeemedClients.forEach { user ->
                        RedeemedCoinsCard(user = user)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            SalirButton {
                navController.navigate(NavRoutes.AdminHome.route) {
                    popUpTo(NavRoutes.AdminHome.route) { inclusive = true }
                }
            }
        }
    }
}