package me.vitalpaw.ui.screens.cliente

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import me.vitalpaw.R
import me.vitalpaw.ui.components.navigationBar.HomeTopBar
import me.vitalpaw.ui.components.navigationBar.RoleBasedDrawerScaffold
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.SessionViewModel
import me.vitalpaw.viewmodels.cliente.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ServiceItem(icon: Int, title: String, description: String, onClick: () -> Unit) {
    var isHovered by remember { mutableStateOf(false) }
    var pressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (pressed || isHovered) 0.97f else 1f,
        label = "scale"
    )
    val alpha by animateFloatAsState(
        targetValue = if (isHovered) 0.95f else 1f,
        label = "alpha"
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        pressed = true
                        tryAwaitRelease()
                        pressed = false
                        onClick()
                    }
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isHovered = true
                        tryAwaitRelease()
                        isHovered = false
                    }
                )
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFCBDFF4)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    fontFamily = quicksandFont,
                    fontSize = 16.sp,
                    color = Color(0xFF19486D)
                )
                Text(
                    text = description,
                    fontFamily = quicksandFont,
                    fontSize = 14.sp,
                    color = Color(0xFF606060)
                )
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController, sessionViewModel: SessionViewModel = hiltViewModel(),viewModel: HomeViewModel = viewModel()) {

    LaunchedEffect(Unit) {
        sessionViewModel.loadUserData()
    }
    val promotions by viewModel.promotions.collectAsState()
    val scrollState = rememberScrollState()
    var showMenu by remember { mutableStateOf(false) }
    var currentPage by remember { mutableStateOf(0) }
    val promoResIds =
        listOf(R.drawable.promo1, R.drawable.promo2, R.drawable.promo3, R.drawable.promo4)

    Button(onClick = {
        navController.navigate(NavRoutes.Shop.route) // ✅ usa la ruta correcta
    }) {
        Text("Ir a tienda")
    }
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentPage = (currentPage + 1) % promoResIds.size
        }
    }

    RoleBasedDrawerScaffold(
        sessionViewModel = sessionViewModel,
        navController = navController
    ) { onMenuClick ->

        LaunchedEffect(Unit) {
            sessionViewModel.loadUserData()
        }

        Scaffold(
            topBar = {
                HomeTopBar(
                    title = "HOME",
                    onMenuClick = onMenuClick,
                    onHomeClick = {
                        navController.navigate(NavRoutes.HomeClient.route) {
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
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(16.dp)
                ) {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Icon(
//                            Icons.Default.Person,
//                            contentDescription = "Perfil",
//                            tint = Color(0xFF19486D)
//                        )
//                        Text(
//                            text = "HOME",
//                            fontSize = 20.sp,
//                            fontFamily = quicksandFont,
//                            color = Color(0xFF19486D)
//                        )
//                        IconButton(
//                            onClick = { showMenu = true },
//                            modifier = Modifier.size(48.dp)
//                        ) {
//                            Icon(
//                                Icons.Default.Menu,
//                                contentDescription = "Menú",
//                                tint = Color(0xFF19486D),
//                                modifier = Modifier.size(36.dp)
//                            )
//                        }
//                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Promociones",
                        fontFamily = quicksandFont,
                        fontSize = 18.sp,
                        color = Color(0xFF3695B9),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = promoResIds[currentPage]),
                            contentDescription = "Promoción",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        promoResIds.forEachIndexed { index, _ ->
                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(8.dp)
                                    .background(
                                        if (index == currentPage) Color(0xFF3695B9) else Color.LightGray,
                                        shape = RoundedCornerShape(50)
                                    )
                                    .clickable { currentPage = index }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Nuestros Servicios",
                        fontFamily = quicksandFont,
                        fontSize = 18.sp,
                        color = Color(0xFF3695B9),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ServiceItem(
                        icon = R.drawable.home1,
                        title = "Grooming",
                        description = "Baño, corte y limpieza profesional para consentir a tu mascota.",
                        onClick = { navController.navigate(NavRoutes.ToAssigned.route) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ServiceItem(
                        icon = R.drawable.home2,
                        title = "Consulta",
                        description = "Revisión médica general y especializada para tu mascota.",
                        onClick = { }
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    ServiceItem(
                        icon = R.drawable.home3,
                        title = "Emergencia",
                        description = "Atención inmediata y cuidados críticos para casos urgentes.",
                        onClick = { }
                    )
                }
                if (showMenu) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x66000000))
                            .pointerInput(Unit) {
                                detectTapGestures { showMenu = false }
                            }
                    )
                }
//        if (showMenu) {
//            Surface(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .fillMaxWidth(0.5f)
//                    .align(Alignment.TopEnd),
//                color = Color.White,
//                shadowElevation = 12.dp,
//                shape = RoundedCornerShape(topStart = 32.dp, bottomStart = 32.dp)
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .fillMaxSize()
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.logo),
//                        contentDescription = "Logo VitalPaw",
//                        modifier = Modifier
//                            .height(130.dp)
//                            .padding(vertical = 16.dp)
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    MenuItem("Registrar mascotas") {
//                        navController.navigate(NavRoutes.RegisterPet.route)
//                        showMenu = false
//                    }
//                    MenuItem("Mis mascotas") {
//                        navController.navigate(NavRoutes.MyPetAssigned.route)
//                        showMenu = false
//                    }
//                    MenuItem("Agendar cita") {
//                        navController.navigate(NavRoutes.RegisterAppointment.route){
//                            popUpTo(NavRoutes.RegisterAppointment.route) { inclusive = true }
//                        }
//
//                        showMenu = false
//                    }
//                    MenuItem("Tienda") {
//                        navController.navigate(NavRoutes.Shop.route)
//                        showMenu = false
//                    }
//                    MenuItem("Mis citas") {
//                        showMenu = false
//                        navController.navigate(NavRoutes.MyPetAppointment.route)
//                    }
//                    Spacer(modifier = Modifier.weight(1f))
//                    Button(
//                        onClick = { showMenu = false
//                                  sessionViewModel.logout()
//                                navController.navigate(NavRoutes.Login.route) {
//                                    popUpTo(0) { inclusive = true }
//                                }},
//                        shape = RoundedCornerShape(24.dp),
//                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCBDFF4))
//                    ) {
//                        Text("Cerrar sesión", color = Color(0xFF19486D), fontFamily = quicksandFont)
//                    }
//                }
//            }
//        }
                //        }
            }
        }
    }
}
//@Composable
//fun MenuItem(text: String, onClick: () -> Unit) {
//    Text( text = text,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 10.dp)
//            .clip(RoundedCornerShape(12.dp))
//            .clickable { onClick() }
//            .background(Color(0xFFF4FAFD))
//            .padding(vertical = 14.dp, horizontal = 12.dp),
//        fontFamily = quicksandFont,
//        color = Color(0xFF19486D),
//        fontSize = 16.sp
//    )
//}

