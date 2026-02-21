package es.aspanion.cognitivo.ui.juegos.pintar.model

import androidx.compose.ui.graphics.Color

data class PlantillaPintar(
    val nombre: String,
    val icono: String,
    val tam: Int,
    val dibujo: List<Int>,
    val colores: List<Color>,
    val imagenReal: Int
)