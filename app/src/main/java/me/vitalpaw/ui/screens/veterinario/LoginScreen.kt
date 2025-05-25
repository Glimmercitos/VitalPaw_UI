package me.vitalpaw.ui.screens.veterinario

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.vitalpaw.R

@Composable
fun LoginScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0ECF7))
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
                    .height(100.dp)
            )
            Text(
                text = "VitalPaw",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF337AB7),
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )
            Surface(
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
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
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2996CC)
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Enter Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Enter Password") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botón iniciar sesión
                Button(
                    onClick = { /* TODO: Acción de login */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C7DBF)),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Iniciar Sesión", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

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

                // Botón de Google
                Button(
                    onClick = { /* TODO: Login con Google */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_background), // icono
                        contentDescription = "Google",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Ingresar con Google", color = Color.White)
                }
            }
            }
        }
    }
}
