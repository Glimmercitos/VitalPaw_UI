package me.vitalpaw.ui.screens.administrador

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import me.vitalpaw.viewmodel.admin.RedeemedCoinsViewModel
import me.vitalpaw.viewmodels.SessionViewModel

@Composable
fun RedeemedCoinsScreen(
    navController: NavController,
    sessionViewModel: SessionViewModel = hiltViewModel(),// Para obtener el token
    viewModel: RedeemedCoinsViewModel = hiltViewModel()

) {
    val token by sessionViewModel.firebaseToken.collectAsState()
    val purchases by viewModel.redeemedPurchases.collectAsState()
    val total by viewModel.totalRedeemedCoins.collectAsState()

    LaunchedEffect(token) {
        token?.let {
            Log.d("RedeemScreen", "Loading with token: $it")
            viewModel.fetchRedeemedData(it)
        }
    }
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
            Text(
                text = "Total canjeado:",
                fontSize = 30.sp,
                color = Color(0xFF3695B9),
                textAlign = TextAlign.Center,
                fontFamily = quicksandFont,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            )

            Text(
                text = "$total pts",
                fontSize = 30.sp,
                color = Color(0xFF3695B9),
                textAlign = TextAlign.Center,
                fontFamily = quicksandFont,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(7.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (purchases.isEmpty()) {
                    Text(
                        text = "Ningún cliente ha canjeado puntos todavía.",
                        textAlign = TextAlign.Center
                    )
                } else {
                    purchases.forEach { user ->
                        RedeemedCoinsCard(name = user.userName, redeemedCoins = user.points)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(10.dp),
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
