package es.aspanion.cognitivo.ui.juegos.pintar.model

data class CeldaPintar(
    val indice: Int,
    val colorCorrecto: Int,
    var colorActual: Int = 0
)