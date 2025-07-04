package me.vitalpaw.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.vitalpaw.R
import me.vitalpaw.viewmodels.SessionViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.RegisterViewModel


@Composable
fun LoginScreen(navController: NavHostController, viewModel: SessionViewModel = hiltViewModel()){
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val showError by viewModel.showError.collectAsState()
    val token by viewModel.firebaseToken.collectAsState()
    val userRole by viewModel.userRole.collectAsState()
    val isLoginSuccess by viewModel.isLoginSuccess.collectAsState()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()
    /*if (viewModel.isLoginSuccess) {
        LaunchedEffect(Unit) {
            navController.navigate("bienvenida") {
                popUpTo("login") { inclusive = true }
            }
        }
    }*/
    val shouldNavigate by remember(isLoginSuccess, token, userRole) {
        mutableStateOf(isLoginSuccess && token != null && userRole.isNotBlank())
    }

    LaunchedEffect(shouldNavigate) {
        if (shouldNavigate) {
            when (userRole) {
                "cliente" -> navController.navigate(NavRoutes.HomeClient.route) {
                    popUpTo(NavRoutes.Login.route) { inclusive = true }
                }
                "veterinario" -> navController.navigate(NavRoutes.Bienvenido.route) {
                    popUpTo(NavRoutes.Login.route) { inclusive = true }
                }
                "admin" -> navController.navigate(NavRoutes.AdminHome.route) {
                    popUpTo(NavRoutes.Login.route) { inclusive = true }
                }
                else -> {
                    // Fallback opcional: podrías mostrar un error o navegar a una pantalla de error
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCBDFF4))
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
        ){
            Spacer(modifier = Modifier.height(60.dp))

            Image (
                painter = painterResource( id  = R.drawable.logo),
                contentDescription = "Logo VitalPaw",
                modifier = Modifier
                    .width(300.dp)
                    .height(200.dp)
            )
            Surface(
                shape = RoundedCornerShape(topStart = 90.dp, topEnd = 0.dp),
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
            ){Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 36.dp)
            ){
                Text(
                    text = "Iniciar Sesión",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF3695B9),
                    fontFamily = quicksandFont
                )

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = viewModel::onEmailChange,
                    label = { Text("Enter Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    isError = showError && email.isBlank()
                )

                Spacer(modifier = Modifier.height(25.dp))

                // Password
                OutlinedTextField(
                    value = password,
                    onValueChange = viewModel::onPasswordChange,
                    label = { Text("Enter Password") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    isError = showError && password.isBlank(),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (isPasswordVisible)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff

                        IconButton(onClick = viewModel::onTogglePasswordVisibility) {
                            Icon(imageVector = image, contentDescription = "Toggle password")
                        }
                    }
                )

                if (showError && (email.isBlank() || password.isBlank())) {
                    Text(
                        text = "Rellenar campos vacíos",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                if (showError && email.isNotBlank() && password.isNotBlank()) {
                    Text(
                        text = errorMsg,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }


                Spacer(modifier = Modifier.height(35.dp))

                // Botón iniciar sesión
                Button(
                    onClick = {
                        viewModel.onLoginClick()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C7DBF)),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "Iniciar Sesión",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        fontFamily = quicksandFont)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Texto de registro
                ClickableText(
                    text = buildAnnotatedString {
                        append("¿No tienes una cuenta? ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold,
                            color = Color(0xFF3C7DBF))) {
                            append("Regístrate")
                        }
                    },
                    onClick = { offset ->
                        val start = "¿No tienes una cuenta? ".length
                        val end = start + "Regístrate".length
                        if (offset in start..end) {
                            navController.navigate(NavRoutes.Register.route)
                        }
                    },
                    modifier = Modifier.padding(top = 8.dp)
                )

            }
            }
        }
    }
}
