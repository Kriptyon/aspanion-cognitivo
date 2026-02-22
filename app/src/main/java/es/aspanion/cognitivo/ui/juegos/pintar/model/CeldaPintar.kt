package es.aspanion.cognitivo.ui.juegos.pintar.model

import androidx.compose.ui.graphics.Color

data class CeldaPintar(
    val id: Int,
    val colorCorrecto: Int,
    var colorActual: Color = Color.White, // Usamos Color directamente
    var estaPintado: Boolean = false,
    var esError: Boolean = false
)