package me.vitalpaw.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.vitalpaw.R
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.SessionViewModel

@Composable
fun SideMenuDrawer(
    navController: NavController,
    showMenu: Boolean,
    onClose: () -> Unit,
    sessionViewModel: SessionViewModel // ⬅️ AÑADÍ ESTO
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // Fondo oscuro al mostrar el menú
        if (showMenu) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable { onClose() }
            )
        }

        AnimatedVisibility(
            visible = showMenu,
            enter = slideInHorizontally(
                initialOffsetX = { fullWidth -> (-fullWidth * 0.6f).toInt() }, // solo desliza el 60%
                animationSpec = tween(durationMillis = 300)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { fullWidth -> (-fullWidth * 0.6f).toInt() }, // misma distancia
                animationSpec = tween(durationMillis = 300)
            )
        ) {
            // Contenedor alineado al borde derecho
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterEnd // ⬅️ IMPORTANTE: al lado derecho
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.5f)
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures { change, dragAmount ->
                                if (dragAmount > 30) { // si se arrastra hacia la derecha lo suficiente
                                    onClose()
                                }
                            }
                        },
                    color = Color.White,
                    shadowElevation = 12.dp,
                    shape = RoundedCornerShape(topStart = 32.dp, bottomStart = 32.dp)
                )
                {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo VitalPaw",
                            modifier = Modifier
                                .height(130.dp)
                                .padding(vertical = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        DrawerMenuItem("Registrar mascotas") {
                            navController.navigate(NavRoutes.RegisterPet.route)
                            onClose()
                        }
                        DrawerMenuItem("Mis mascotas") {
                            navController.navigate(NavRoutes.MyPetAssigned.route)
                            onClose()
                        }
                        DrawerMenuItem("Agendar cita") {
                            navController.navigate(NavRoutes.RegisterAppointment.route)
                            onClose()
                        }
                        DrawerMenuItem("Mis citas") {
                            navController.navigate(NavRoutes.MyPetAppointment.route)
                            onClose()
                        }
                        DrawerMenuItem("Tienda") {
                            navController.navigate(NavRoutes.Shop.route)
                            onClose()
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = {
                                sessionViewModel.logout() // ⬅️ llama al ViewModel
                                navController.navigate(NavRoutes.Login.route) {
                                    popUpTo(0) // Limpia el back stack
                                }
                                onClose()
                            },
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCBDFF4))
                        ) {
                            Text("Cerrar sesión", color = Color(0xFF19486D), fontFamily = quicksandFont)
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun DrawerMenuItem(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable { onClick() }
            .background(Color(0xFFF4FAFD), RoundedCornerShape(12.dp))
            .padding(vertical = 14.dp, horizontal = 12.dp),
        fontFamily = quicksandFont,
        color = Color(0xFF19486D),
        fontSize = 16.sp
    )
}
