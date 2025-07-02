package me.vitalpaw.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.vitalpaw.viewmodel.UserViewModel
import me.vitalpaw.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.vitalpaw.ui.components.admin.UserRolCard
import me.vitalpaw.ui.components.buttons.GuardarCitaButton
import me.vitalpaw.ui.components.buttons.SalirButton
import me.vitalpaw.ui.components.modal.MarkAsCompleteDialog
import me.vitalpaw.ui.components.modal.ErrorDialog
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import me.vitalpaw.viewmodels.SessionViewModel

@Composable
fun AssignVeterinarianScreen(
    navController: NavController,
    userId: String?,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val query = userViewModel.searchQuery
    val users = userViewModel.filteredUsers
    val selectedUser = userViewModel.selectedUser
    val isVet = userViewModel.isVeterinarian()
    val token by sessionViewModel.firebaseToken.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(userId, token) {
        if (userId != null && token != null && userViewModel.selectedUser == null) {
            val user = userViewModel.getUserById(userId, token!!)
            user?.let { userViewModel.selectUser(it) }
        }
    }

    MarkAsCompleteDialog(
        show = showDialog,
        onDismiss = { showDialog = false },
        onConfirm = {
            token?.let {
                userViewModel.changeUserRole(it, context)
            }
            showDialog = false
        },
        title = "Cambiar rol a ${userViewModel.getPendingRole()}?",
        message = "¿Estás seguro de cambiar el rol a ${selectedUser?.email}?"
    )

    ErrorDialog(
        show = userViewModel.showErrorDialog,
        onDismiss = { userViewModel.dismissError() },
        message = userViewModel.errorMessage
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 27.dp, vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.defaultuserimage),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = query,
                onValueChange = {
                    token?.let { tk ->
                        userViewModel.onSearchQueryChanged(it, tk)
                    }
                },
                label = { Text("Correo") },
                shape = RoundedCornerShape(20.dp),
                textStyle = LocalTextStyle.current.copy(fontFamily = quicksandFont),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.Gray,
                    disabledContainerColor = Color.Transparent,
                    disabledBorderColor = Color(0xFF487FE1),
                    focusedBorderColor = Color(0xFF6E7AE6),
                    unfocusedBorderColor = Color(0xFF6E7AE6)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Seleccionar usuario",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF4682B4),
                fontFamily = quicksandFont
            )
            Spacer(modifier = Modifier.height(12.dp))

            if (selectedUser == null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(5f),
                ) {
                    items(users) { user ->
                        UserRolCard(
                            user = user,
                            onClick = { userViewModel.selectUser(user) },
                            isSelected = false
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    UserRolCard(
                        user = selectedUser,
                        onClick = {},
                        isSelected = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            selectedUser?.let { user ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(
                        checked = isVet,
                        onCheckedChange = { checked ->
                            val newRole = if (checked) "veterinario" else "cliente"
                            userViewModel.selectUser(user.copy(role = newRole))
                            Log.e("NUEVO USER", "${userViewModel.selectUser(user.copy(role = newRole))}")
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFFDFE7FF),
                            checkedTrackColor = Color(0xFF4682B4),
                            uncheckedThumbColor = Color(0xFF4682B4),
                            uncheckedTrackColor = Color(0xFFDFE7FF)
                        )
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text("Asignar rol de veterinario", fontFamily = quicksandFont)
                }

                Spacer(modifier = Modifier.height(17.dp))
                Text("Usuario seleccionado: ${user.email}", fontFamily = quicksandFont)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SalirButton {
                navController.navigate(NavRoutes.AllVets.route) {
                    popUpTo(NavRoutes.AllVets.route) { inclusive = true }
                }
            }

            GuardarCitaButton(
                onClick = {
                    if (selectedUser != null) showDialog = true
                }
            )
        }
    }
}
