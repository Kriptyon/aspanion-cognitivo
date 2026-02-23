package es.aspanion.cognitivo.ui.menu

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.aspanion.cognitivo.ui.components.BotonMenu
import androidx.compose.ui.graphics.Color

@Composable
fun PantallaMenuJuegos(
    nivel: String,
    alElegirJuego: (String) -> Unit,
    alCambiarEdad: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(onClick = alCambiarEdad, modifier = Modifier.align(Alignment.End)) {
            Text("⚙️ Cambiar Edad")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¿A qué jugamos?",
            fontSize = 30.sp,
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(40.dp))

        // BOTÓN 1: MEMORY
        BotonMenu("Memory 🧠", Color(0xFFC8E6C9)) { alElegirJuego("memory") }

        Spacer(modifier = Modifier.height(16.dp))

        // BOTÓN 2: PINTAR
        BotonMenu("Pintar Animales 🎨", Color(0xFFFFE0B2)) { alElegirJuego("seleccion_plantilla") }

        Spacer(modifier = Modifier.height(16.dp))

        // BOTÓN 3: SOMBRAS CHINAS (¡El nuevo!)
        BotonMenu("Sombras Chinas 🌑", Color(0xFFD1C4E9)) { alElegirJuego("sombras") }
        // BOTÓN 4: SUDOKU (Solo para medianos y mayores)
        if (nivel != "peque") {
            Spacer(modifier = Modifier.height(16.dp))
            BotonMenu("Sudoku 🦁", Color(0xFFFFF9C4)) { alElegirJuego("sudoku") }
        }
    }
}