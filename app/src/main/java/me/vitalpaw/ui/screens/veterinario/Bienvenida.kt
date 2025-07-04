package me.vitalpaw.ui.screens.veterinario

import androidx.browser.trusted.Token
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import me.vitalpaw.R
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.SessionViewModel

@Composable
fun BienvenidoScreen(navController: NavController, sessionViewModel: SessionViewModel = hiltViewModel()) {
    val token by sessionViewModel.firebaseToken.collectAsState()
    LaunchedEffect(Unit) {
        sessionViewModel.loadUserData()
        delay(2000)
        navController.navigate(NavRoutes.Home.route){
            popUpTo(NavRoutes.Bienvenido.route){inclusive = true}
        }
    }
    val user = sessionViewModel.user.collectAsState()


    val name = user.value?.name?: "Veterinaria"
    val gender = user.value?.gender?: "Female"

    val greeting = if (gender == "Female") "Bienvenida Dra." else "Bienvenido Dr."

    Box(
        modifier = Modifier.fillMaxSize().background(Color.White))
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo VitalPaw",
                modifier = Modifier
                    .width(350.dp)
                    .height(180.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "$greeting $name",
                fontSize = 27.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF6980BF),
                fontFamily = quicksandFont,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}