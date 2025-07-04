package me.vitalpaw.ui.screens.administrador

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.vitalpaw.viewmodels.admin.RechargeCoinsViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import me.vitalpaw.ui.components.buttons.GuardarCitaButton
import me.vitalpaw.ui.components.buttons.SalirButton
import me.vitalpaw.ui.components.modal.ErrorDialog
import me.vitalpaw.ui.components.modal.MarkAsCompleteDialog
import me.vitalpaw.ui.navigation.NavRoutes
import android.widget.Toast
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import me.vitalpaw.ui.components.navigationBar.HomeTopBar
import me.vitalpaw.ui.components.navigationBar.RoleBasedDrawerScaffold
import me.vitalpaw.viewmodels.SessionViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RechargeVitalCoinsScreen(
    navController: NavHostController,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    viewModel: RechargeCoinsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val emailQuery = viewModel.emailQuery
    val filteredClients = viewModel.filteredClients
    val selectedUser = viewModel.selectedUser
    val amount = viewModel.amount

    var expanded by remember { mutableStateOf(false) }
    val token by sessionViewModel.firebaseToken.collectAsState()

    RoleBasedDrawerScaffold(
        sessionViewModel = sessionViewModel,
        navController = navController
    ) { onMenuClick ->

        Scaffold(
            topBar = {
                HomeTopBar(
                    title = "RECARGAR MONEDAS",
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f), // contenido hacia abajo
            horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(300.dp)
                    .height(200.dp)
            )
        }

        ExposedDropdownMenuBox(
                expanded = expanded && filteredClients.isNotEmpty() && emailQuery.isNotBlank(),
                onExpandedChange = {
                    if (emailQuery.isNotBlank()) expanded = !expanded
                }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = emailQuery,
                onValueChange = {
                    viewModel.onEmailChanged(it, token!!)
                    expanded = true
                },
                label = { Text("Buscar correo cliente") },
                shape = RoundedCornerShape(20.dp),
                textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.Gray,
                    disabledContainerColor = Color.Transparent,
                    disabledBorderColor = Color(0xFF487FE1),
                    focusedBorderColor = Color(0xFF6E7AE6),
                    unfocusedBorderColor = Color(0xFF6E7AE6)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                filteredClients.forEach { user ->
                    DropdownMenuItem(
                        text = { Text("${user.name} - ${user.email}") },
                        onClick = {
                            viewModel.selectUser(user)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { viewModel.onAmountChanged(it) },
            label = { Text("Monto a recargar") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Gray,
                disabledContainerColor = Color.Transparent,
                disabledBorderColor = Color(0xFF487FE1),
                focusedBorderColor = Color(0xFF6E7AE6),
                unfocusedBorderColor = Color(0xFF6E7AE6)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )
    }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SalirButton {
                navController.navigate(NavRoutes.AdminHome.route) {
                    popUpTo(NavRoutes.AdminHome.route) { inclusive = true }
                }
            }

            GuardarCitaButton(onClick = {
                viewModel.validateFields()
            })

        }

        MarkAsCompleteDialog(
            show = viewModel.showConfirmDialog,
            title = viewModel.confirmDialogTitle,
            message = viewModel.confirmDialogMessage,
            onDismiss = { viewModel.dismissDialogs() },
            onConfirm = {
                selectedUser?.id?.let { userId ->
                    viewModel.postVitalCoins(token!!)
                    Toast.makeText(context, "Recarga realizada", Toast.LENGTH_SHORT).show()
                    viewModel.resetFields()
                } ?: run {
                    Toast.makeText(context, "Error: Usuario no seleccionado", Toast.LENGTH_SHORT).show()
                }
                viewModel.dismissDialogs()
            }
        )

        ErrorDialog(
            show = viewModel.showErrorDialog,
            onDismiss = { viewModel.dismissDialogs() },
            message = viewModel.errorMessage
        )

    }}
    }
}
