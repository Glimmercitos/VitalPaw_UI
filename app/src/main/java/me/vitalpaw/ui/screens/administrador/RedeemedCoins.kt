package me.vitalpaw.ui.screens.administrador

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
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
import me.vitalpaw.ui.components.admin.RedeemedCoinsCard
import me.vitalpaw.ui.components.buttons.SalirButton
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import me.vitalpaw.ui.components.navigationBar.HomeTopBar
import me.vitalpaw.ui.components.navigationBar.RoleBasedDrawerScaffold
import me.vitalpaw.viewmodel.admin.RedeemedCoinsViewModel
import me.vitalpaw.viewmodels.SessionViewModel

@Composable
fun RedeemedCoinsScreen(
    navController: NavHostController,
    sessionViewModel: SessionViewModel = hiltViewModel(),// Para obtener el token
    viewModel: RedeemedCoinsViewModel = hiltViewModel()

) {
    val token by sessionViewModel.firebaseToken.collectAsState()
    val purchases by viewModel.redeemedPurchases.collectAsState()
    val total by viewModel.totalRedeemedCoins.collectAsState()
    val isLoading by sessionViewModel.isLoading.collectAsState()

    LaunchedEffect(token) {
        token?.let {
            Log.d("RedeemScreen", "Loading with token: $it")
            viewModel.fetchRedeemedData(it)
        }
    }
    RoleBasedDrawerScaffold(
        sessionViewModel = sessionViewModel,
        navController = navController
    ) { onMenuClick ->

        Scaffold(
            topBar = {
                HomeTopBar(
                    title = "GANANCIAS",
                    onMenuClick = onMenuClick,
                    onHomeClick = {
                        navController.navigate(NavRoutes.AdminHome.route) {
                            popUpTo(NavRoutes.Login.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                )
            },
            containerColor = Color.White
        ) { paddingValues ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Color(0xFF3695B9)
                        )
                    }

                    else -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Total canjeado:",
                                    fontSize = 30.sp,
                                    color = Color(0xFF3695B9),
                                    fontFamily = quicksandFont,
                                    textAlign = TextAlign.Center
                                )

                                Text(
                                    text = "$total pts",
                                    fontSize = 30.sp,
                                    color = Color(0xFF3695B9),
                                    fontFamily = quicksandFont,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 10.dp)
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(7.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (purchases.isEmpty()) {
                                    Text(
                                        text = "Ningún cliente ha canjeado puntos todavía.",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(top = 16.dp)
                                    )
                                } else {
                                    purchases.forEach { user ->
                                        RedeemedCoinsCard(
                                            name = user.userName,
                                            redeemedCoins = user.points
                                        )
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
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
                }
            }
        }
    }
}

