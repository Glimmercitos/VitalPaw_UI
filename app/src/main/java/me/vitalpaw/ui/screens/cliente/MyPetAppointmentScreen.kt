package me.vitalpaw.ui.screens.cliente

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import me.vitalpaw.R
import me.vitalpaw.models.Appointment
import me.vitalpaw.ui.components.MyPetAppointmentCard
import me.vitalpaw.ui.components.buttons.AddAppointmentButton
import me.vitalpaw.ui.components.modal.MarkAsCompleteDialog
import me.vitalpaw.ui.components.navigationBar.HomeTopBar
import me.vitalpaw.ui.components.navigationBar.RoleBasedDrawerScaffold
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.SessionViewModel
import me.vitalpaw.viewmodels.cliente.MyAppointmentsPetViewModel

@Composable
fun MyPetAppointmentScreenContent(
    appointments: List<Appointment>,
    onBack: () -> Unit = {},
    onNewAppointment: () -> Unit = {},
    onDeleteClick: (Appointment) -> Unit = {}
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {

//            Text(
//                text = "MIS CITAS",
//                style = MaterialTheme.typography.bodyMedium,
//                fontFamily = quicksandFont,
//                color = Color.Gray,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(
//                        color = Color(0xFFF5F5F5),
//                        shape = RoundedCornerShape(25.dp)
//                    )
//                    .padding(vertical = 20.dp),
//                textAlign = TextAlign.Center
//            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                Spacer(modifier = Modifier.height(24.dp))

                if (appointments.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay citas registradas",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.LightGray
                        )
                    }
                } else {
                    appointments.forEach { appointment ->
                        MyPetAppointmentCard(
                            appointment = appointment,
                            onDeleteClick = {
                                onDeleteClick(appointment) // <- aquí
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // 🔽 Botón al final del scroll y centrado
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AddAppointmentButton(
                        text = "Agendar nueva cita",
                        onClick = onNewAppointment,
                        modifier = Modifier.widthIn(min = 180.dp)
                    )
                }
            }
            AddAppointmentButton(
                text = "Agendar nueva cita",
                onClick = onNewAppointment,
                modifier = Modifier.widthIn(min = 180.dp)
            )
        }
    }
}
@Composable
fun MyPetAppointmentScreen(
    navController: NavHostController,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    viewModel: MyAppointmentsPetViewModel = hiltViewModel()
) {
    val token by sessionViewModel.firebaseToken.collectAsState()
    val appointments = viewModel.appointments.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedAppointmentId by remember { mutableStateOf<String?>(null) }

    // Cargar citas al tener token
    LaunchedEffect(token) {
        token?.let {
            viewModel.loadMyAppointments(it)
        }
    }

    RoleBasedDrawerScaffold(
        sessionViewModel = sessionViewModel,
        navController = navController
    ) { onMenuClick ->

        Scaffold(
            topBar = {
                HomeTopBar(
                    title = "MIS CITAS",
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                MyPetAppointmentScreenContent(
                    appointments = appointments.value,
                    onBack = { navController.popBackStack() },
                    onNewAppointment = {
                        navController.navigate(NavRoutes.RegisterAppointment.route)
                    },
                    onDeleteClick = {
                        selectedAppointmentId = it.id
                        showDialog = true
                    }
                )
            }

            // Diálogo de eliminación
            if (showDialog && selectedAppointmentId != null) {
                MarkAsCompleteDialog(
                    show = showDialog,
                    onDismiss = {
                        showDialog = false
                        selectedAppointmentId = null
                    },
                    onConfirm = {
                        selectedAppointmentId?.let {
                            token?.let { tkn ->
                                viewModel.deleteAppointment(tkn, it)
                            }
                        }
                        showDialog = false
                        selectedAppointmentId = null
                    },
                    title = "Eliminar cita",
                    message = "¿Seguro que deseas eliminar esta cita?",
                    confirmText = "Sí, eliminar",
                    dismissText = "Cancelar"
                )
            }
        }
    }
}

