package me.vitalpaw.ui.components.modal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.vitalpaw.R
import me.vitalpaw.ui.theme.quicksandFont

@Composable
fun ErrorDialog(
    show: Boolean,
    onDismiss: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDB4453)),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = "OK",
                            fontFamily = quicksandFont,
                            fontSize = 20.sp
                        )
                    }
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.xbtn),
                        contentDescription = "Error Icon",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(50))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Error al asignar cita",
                        fontFamily = quicksandFont,
                        fontSize = 20.sp,
                        color = Color(0xFFDB4453)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Vuelve a intentar",
                        fontFamily = quicksandFont,
                        fontSize = 20.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(20.dp)
        )
    }
}
