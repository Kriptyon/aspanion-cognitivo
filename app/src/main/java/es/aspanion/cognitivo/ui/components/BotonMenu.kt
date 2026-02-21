package es.aspanion.cognitivo.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BotonMenu(
    texto: String,
    color: Color,
    alClick: () -> Unit
) {
    Button(
        onClick = alClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Text(
            texto,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}