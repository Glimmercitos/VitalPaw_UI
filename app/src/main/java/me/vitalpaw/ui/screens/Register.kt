package me.vitalpaw.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import androidx.navigation.NavController
import me.vitalpaw.R
import me.vitalpaw.ui.navigation.NavRoutes
import me.vitalpaw.ui.theme.quicksandFont
import me.vitalpaw.viewmodels.RegisterViewModel

@Preview(showBackground = true)
@Composable
fun PreviewRegister() {
    Register(navController = NavController(LocalContext.current))
    }

@Composable
fun Register(
    navController: NavController,
    viewModel: RegisterViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
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
                        .verticalScroll(scrollState)
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
                        value = viewModel.name,
                        onValueChange = viewModel::onNameChange,
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        isError = viewModel.showError && viewModel.name.isBlank()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = viewModel.email,
                        onValueChange = viewModel::onEmailChange,
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        isError = viewModel.showError && viewModel.email.isBlank()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = viewModel.gender,
                        onValueChange = {},
                        enabled = false,
                        readOnly = true,
                        label = { Text("Gender", color = Color.DarkGray) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledBorderColor = if (viewModel.showError && viewModel.gender.isBlank()) Color.Red else Color(0xFF7C7C7C),
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
                                        selected = viewModel.gender == "Female",
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
                                        selected = viewModel.gender == "Male",
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
                        value = viewModel.password,
                        onValueChange = viewModel::onPasswordChange,
                        label = { Text("Enter Password") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        isError = viewModel.showError && viewModel.password.isBlank(),
                        visualTransformation = if (viewModel.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon = if (viewModel.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { viewModel.onTogglePasswordVisibility() }) {
                                Icon(imageVector = icon, contentDescription = "Toggle password")
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = viewModel.confirmPassword,
                        onValueChange = viewModel::onConfirmPasswordChange,
                        label = { Text("Confirm Password") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        isError = viewModel.showError && (viewModel.confirmPassword.isBlank() || viewModel.password != viewModel.confirmPassword),
                        visualTransformation = if (viewModel.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon = if (viewModel.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { viewModel.onTogglePasswordVisibility() }) {
                                Icon(imageVector = icon, contentDescription = "Toggle password")
                            }
                        }
                    )

                    if (viewModel.showError) {
                        Text(
                            text = if (viewModel.password != viewModel.confirmPassword && viewModel.confirmPassword.isNotBlank())
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
                            if (!viewModel.showError) {
                                navController.navigate(NavRoutes.Login.route)
                            }
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
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
