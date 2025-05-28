package me.vitalpaw.ui.screens.veterinario

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.vitalpaw.R

val quicksandFont = FontFamily(Font(R.font.quicksand_bold))
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RegistrerScreen() {
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
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Logo VitalPaw",
                modifier = Modifier.height(100.dp)
            )
            Surface(
                shape = RoundedCornerShape(topStart = 0.dp, topEnd = 90.dp),
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
                        text = "Registrate",
                        fontFamily = quicksandFont,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2996CC)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    val borderColor = Color(0xFFE0E0E0)

                    listOf("Name", "Email", "Tel", "Enter Password", "Confirm Password").forEach { label ->
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            label = {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = borderColor,
                                unfocusedBorderColor = borderColor
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C7DBF)),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = "Registrarse", color = Color.White, fontFamily = quicksandFont)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        buildAnnotatedString {
                            append("¿Ya tienes una cuenta? ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Inicia Sesión")
                            }
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "Google",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Registrate con Google", color = Color.White, fontFamily = quicksandFont)
                    }
                }
            }
        }
    }
}
