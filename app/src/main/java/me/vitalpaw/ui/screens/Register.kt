package me.vitalpaw.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import me.vitalpaw.R
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.RegisterViewModel
import androidx.compose.runtime.getValue


@Composable
fun Register(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val showError by viewModel.showError.collectAsState()
    val gender by viewModel.gender.collectAsState()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()
    val isRegisterSuccess by viewModel.isRegisterSuccess.collectAsState()

    LaunchedEffect(isRegisterSuccess) {
        if (isRegisterSuccess) {
            navController.navigate(NavRoutes.Login.route) {
                popUpTo(NavRoutes.Register.route) { inclusive = true }
            }
            viewModel.resetRegisterState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCBDFF4))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo VitalPaw",
                modifier = Modifier.height(140.dp)
            )

            Surface(
                shape = RoundedCornerShape(topStart = 0.dp, topEnd = 50.dp),
                color = Color.White,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 36.dp)
                ) {
                    Text(
                        text = "Regístrate",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF2996CC),
                        fontFamily = quicksandFont
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = viewModel::onNameChange,
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        isError = showError && name.isBlank()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = viewModel::onEmailChange,
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        isError = showError && email.isBlank()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = gender,
                        onValueChange = viewModel::onGenderChange,
                        enabled = false,
                        readOnly = true,
                        label = { Text("Gender", color = Color.DarkGray) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledBorderColor = if (showError && gender.isBlank()) Color.Red else Color(0xFF7C7C7C),
                            disabledTextColor = Color(0xFF7C7C7C),
                            disabledLabelColor = Color(0xFF7C7C7C),
                            disabledTrailingIconColor = Color(0xFF7C7C7C)
                        ),
                        trailingIcon = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.padding(end = 20.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = gender == "Female",
                                        onClick = { viewModel.onGenderChange("Female") },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = Color.DarkGray,
                                            unselectedColor = Color(0xFF7C7C7C)
                                        )
                                    )
                                    Text("F", color = Color(0xFF7C7C7C), fontFamily = quicksandFont, fontSize = 17.sp)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = gender == "Male",
                                        onClick = { viewModel.onGenderChange("Male") },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = Color.DarkGray,
                                            unselectedColor = Color(0xFF7C7C7C)
                                        )
                                    )
                                    Text("M", color = Color(0xFF7C7C7C), fontFamily = quicksandFont, fontSize = 17.sp)
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = viewModel::onPasswordChange,
                        label = { Text("Enter Password") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        isError = showError && password.isBlank(),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { viewModel.onTogglePasswordVisibility() }) {
                                Icon(imageVector = icon, contentDescription = "Toggle password")
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = viewModel::onConfirmPasswordChange,
                        label = { Text("Confirm Password") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        isError = showError && (confirmPassword.isBlank() || password != confirmPassword),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { viewModel.onTogglePasswordVisibility() }) {
                                Icon(imageVector = icon, contentDescription = "Toggle password")
                            }
                        }
                    )

                    if (showError) {
                        Text(
                            text = if (password != confirmPassword && confirmPassword.isNotBlank())
                                "Las contraseñas no coinciden"
                            else
                                "Rellenar todos los campos",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.onRegisterClick()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C7DBF)),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Registrarse", color = Color.White, fontFamily = quicksandFont)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = { navController.navigate(NavRoutes.Login.route) }) {
                        Text(
                            buildAnnotatedString {
                                append("¿Ya tienes una cuenta? ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Inicia Sesión")
                                }
                            },
                            fontSize = 14.sp, color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
