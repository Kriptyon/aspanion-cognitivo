package es.aspanion.cognitivo.ui.inicio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.aspanion.cognitivo.R
import es.aspanion.cognitivo.ui.components.BotonMenu

@Composable
fun PantallaInicioAspanion(onEdadElegida: (String) -> Unit) {
    // Color rosa oficial de Aspanion
    val rosaAspanion = Color(0xFFE91E63)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // 1. Logo superior
        Image(
            painter = painterResource(R.drawable.logo_aspanion),
            contentDescription = "Logo Aspanion",
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth()
        )

        // 2. BLOQUE CENTRAL: Martín, Lola y el saludo
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.martin_y_lola),
                contentDescription = "Martín y Lola",
                modifier = Modifier
                    .height(280.dp)
                    .fillMaxWidth()
                    // Desplazamos la imagen 40dp a la izquierda
                    .offset(x = (-40).dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¡Hola, Valiente!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = rosaAspanion
            )
        }

        // 3. Botones de selección de edad
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            BotonMenu(
                texto = "Peques (2-4)",
                color = Color(0xFFC8E6C9) // Verde pastel
            ) {
                onEdadElegida("peque")
            }

            Spacer(modifier = Modifier.height(12.dp))

            BotonMenu(
                texto = "Medianos (5-7)",
                color = Color(0xFFFFF9C4) // Amarillo pastel
            ) {
                onEdadElegida("mediano")
            }

            Spacer(modifier = Modifier.height(12.dp))

            BotonMenu(
                texto = "Mayores (+8)",
                color = Color(0xFFFFE0B2) // Naranja pastel
            ) {
                onEdadElegida("experto")
            }
        }
    }
}