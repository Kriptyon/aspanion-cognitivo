package es.aspanion.cognitivo.ui.juegos.sombras.model

data class NivelSombras(
    val emojiSombra: String,   // El animal que queremos adivinar
    val imagenReal: Int,      // La foto JPEG correcta
    val opciones: List<Int>   // Las 3 fotos que se muestran abajo
)