package es.aspanion.cognitivo.ui.juegos.memory.model

data class Carta(
    val id: Int,
    val icono: String,
    var descubierta: Boolean = false,
    var emparejada: Boolean = false
)