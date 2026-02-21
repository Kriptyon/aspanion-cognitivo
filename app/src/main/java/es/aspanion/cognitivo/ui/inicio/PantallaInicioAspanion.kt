package es.aspanion.cognitivo.ui.inicio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.aspanion.cognitivo.R
import es.aspanion.cognitivo.ui.components.BotonMenu

@Composable
fun PantallaInicioAspanion(onEdadElegida: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(R.drawable.logo_aspanion),
            contentDescription = null,
            modifier = Modifier.height(60.dp)
        )

        Text(
            "¡Hola, Valiente!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Column {
            BotonMenu("Peques (2-4)", MaterialTheme.colorScheme.secondary) {
                onEdadElegida("peque")
            }
            Spacer(modifier = Modifier.height(12.dp))
            BotonMenu("Medianos (5-7)", MaterialTheme.colorScheme.tertiary) {
                onEdadElegida("mediano")
            }
            Spacer(modifier = Modifier.height(12.dp))
            BotonMenu("Mayores (+8)", MaterialTheme.colorScheme.primary) {
                onEdadElegida("experto")
            }
        }
    }
}