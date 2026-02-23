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
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(onClick = alCambiarEdad, modifier = Modifier.align(Alignment.End)) {
            Text("⚙️ Cambiar Edad")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "¿A qué jugamos?", fontSize = 30.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(30.dp))

        // FILA 1
        BotonMenu("Memory 🧠", Color(0xFFC8E6C9)) { alElegirJuego("memory") }
        Spacer(modifier = Modifier.height(12.dp))

        // FILA 2
        BotonMenu("Pintar Animales 🎨", Color(0xFFFFE0B2)) { alElegirJuego("seleccion_plantilla") }
        Spacer(modifier = Modifier.height(12.dp))

        // FILA 3
        BotonMenu("Sombras Chinas 🌑", Color(0xFFD1C4E9)) { alElegirJuego("sombras") }
        Spacer(modifier = Modifier.height(12.dp))

        // FILA 4: EL NUEVO SIMÓN
        BotonMenu("Simón Animal 🎵", Color(0xFFF8BBD0)) { alElegirJuego("simon") }

        // FILA 5: SUDOKU (Solo mayores)
        if (nivel != "peque") {
            Spacer(modifier = Modifier.height(12.dp))
            BotonMenu("Sudoku 🦁", Color(0xFFFFF9C4)) { alElegirJuego("sudoku") }
        }
    }
}