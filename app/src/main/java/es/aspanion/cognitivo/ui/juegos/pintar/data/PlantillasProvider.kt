package es.aspanion.cognitivo.ui.juegos.pintar.data

import androidx.compose.ui.graphics.Color
import es.aspanion.cognitivo.R
import es.aspanion.cognitivo.ui.juegos.pintar.model.PlantillaPintar

fun obtenerPlantillas(nivel: String): List<PlantillaPintar> {
    val naranja = Color(0xFFFF5722)
    val blancoHueso = Color(0xFFF5F5F5)
    val rosaIconos = Color(0xFFFF80AB)

    return when(nivel) {
        "peque" -> listOf(
            PlantillaPintar("Pollito", "🐥", 5, listOf(0,0,0,0,0, 0,1,1,0,0, 0,1,1,2,0, 0,1,1,0,0, 0,0,0,0,0), listOf(Color.White, Color.Yellow, Color(0xFFFF9800)), R.drawable.pollito_real),
            PlantillaPintar("Rana", "🐸", 5, listOf(1,0,1,0,1, 1,1,1,1,1, 1,2,2,2,1, 0,1,1,1,0, 1,0,1,0,1), listOf(Color.White, Color.Green, rosaIconos), R.drawable.rana_real)
        )
        "mediano" -> listOf(
            PlantillaPintar("Zorro", "🦊", 10, listOf(0,1,0,0,0,0,0,0,1,0, 0,1,1,0,0,0,0,1,1,0, 1,1,1,1,1,1,1,1,1,1, 1,1,1,1,1,1,1,1,1,1, 1,1,3,1,1,1,1,3,1,1, 1,2,2,2,2,2,2,2,2,1, 0,1,2,2,2,2,2,2,1,0, 0,0,1,2,2,2,2,1,0,0, 0,0,0,1,2,2,1,0,0,0, 0,0,0,0,1,1,0,0,0,0), listOf(Color.White, naranja, blancoHueso, Color.Black), R.drawable.zorro_real),
            PlantillaPintar("Oso Panda", "🐼", 10, listOf(0,1,1,0,0,0,0,1,1,0, 1,1,1,1,0,0,1,1,1,1, 1,1,1,1,1,1,1,1,1,1, 1,3,3,1,1,1,1,3,3,1, 1,3,3,1,1,1,1,3,3,1, 1,1,1,2,2,2,2,1,1,1, 1,1,2,2,2,2,2,2,1,1, 0,1,2,2,2,2,2,2,1,0, 0,0,1,1,1,1,1,1,0,0, 0,0,0,0,0,0,0,0,0,0), listOf(Color.White, Color.Black, Color(0xFFEEEEEE), Color(0xFF424242)), R.drawable.panda_real)
        )
        else -> listOf(
            PlantillaPintar("León", "🦁", 12, listOf(0,0,2,2,2,2,2,2,2,2,0,0, 0,2,2,2,2,2,2,2,2,2,2,0, 2,2,1,1,1,1,1,1,1,1,2,2, 2,2,1,1,1,1,1,1,1,1,2,2, 2,1,1,3,1,1,1,1,3,1,1,2, 2,1,1,1,1,1,1,1,1,1,1,2, 2,1,1,1,1,4,4,1,1,1,1,2, 2,1,1,1,4,3,3,4,1,1,1,2, 2,2,1,1,4,4,4,4,1,1,2,2, 0,2,2,1,1,5,5,1,1,2,2,0, 0,0,2,2,1,1,1,1,2,2,0,0, 0,0,0,2,2,2,2,2,2,0,0,0), listOf(Color.White, Color(0xFFFFC107), Color(0xFF795548), Color.Black, Color(0xFFFFF9C4), Color(0xFFFF80AB)), R.drawable.leon_real)
        )
    }
}