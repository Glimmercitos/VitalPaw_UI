/*package me.vitalpaw.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.IconButton
import androidx.navigation.NavHostController
import me.vitalpaw.ui.theme.quicksandFont


@Preview(showBackground = true)
@Composable
fun LoginScreen(viewModel: NavHostController = viewModel()){
    val email = viewModel.email
    val password = viewModel.password
    val showError = viewModel.showError

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
                    fontSize = 30.sp,
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

                Spacer(modifier = Modifier.height(20.dp))

                // Password
                OutlinedTextField(
                    value = password,
                    onValueChange = viewModel::onPasswordChange,
                    label = { Text("Enter Password") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    isError = showError && password.isBlank(),
                    visualTransformation = if (viewModel.isPasswordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (viewModel.isPasswordVisible)
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

                Spacer(modifier = Modifier.height(35.dp))

                // Botón iniciar sesión
                Button(
                    onClick = viewModel::onLoginClick,
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
                Text(
                    buildAnnotatedString {
                        append("¿No tienes una cuenta? ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Regístrate")
                        }
                    },
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { /* TODO: Login con Google */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Google",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Ingresar con Google",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White)
                }
            }
            }
        }
    }
}

*/