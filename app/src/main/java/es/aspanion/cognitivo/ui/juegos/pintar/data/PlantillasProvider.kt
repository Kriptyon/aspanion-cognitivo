package es.aspanion.cognitivo.ui.juegos.pintar.data

import androidx.compose.ui.graphics.Color
import es.aspanion.cognitivo.R
import es.aspanion.cognitivo.ui.juegos.pintar.model.PlantillaPintar

fun obtenerPlantillas(nivel: String): List<PlantillaPintar> {

    return when (nivel) {

        "peque" -> listOf(
            PlantillaPintar(
                nombre = "Pollito",
                icono = "🐥",
                tam = 5,
                dibujo = listOf(
                    0,0,0,0,0,
                    0,1,1,0,0,
                    0,1,1,2,0,
                    0,1,1,0,0,
                    0,0,0,0,0
                ),
                colores = listOf(
                    Color.White,
                    Color.Yellow,
                    Color(0xFFFF9800)
                ),
                imagenReal = R.drawable.pollito_real
            )
        )

        else -> listOf(
            PlantillaPintar(
                nombre = "Zorro",
                icono = "🦊",
                tam = 10,
                dibujo = listOf(
                    0,1,0,0,0,0,0,0,1,0,
                    0,1,1,0,0,0,0,1,1,0,
                    1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,
                    1,1,3,1,1,1,1,3,1,1,
                    1,2,2,2,2,2,2,2,2,1,
                    0,1,2,2,2,2,2,2,1,0,
                    0,0,1,2,2,2,2,1,0,0,
                    0,0,0,1,2,2,1,0,0,0,
                    0,0,0,0,1,1,0,0,0,0
                ),
                colores = listOf(
                    Color.White,
                    Color(0xFFFF5722),
                    Color(0xFFF5F5F5),
                    Color.Black
                ),
                imagenReal = R.drawable.zorro_real
            )
        )
    }
}