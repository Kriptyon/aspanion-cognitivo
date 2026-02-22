package es.aspanion.cognitivo.ui.juegos.memory.model

data class Carta(
    val id: Int,
    val contenido: String, // Cambiado de 'icono' a 'contenido'
    var estaVolteada: Boolean = false // Cambiado de 'descubierta' a 'estaVolteada'
)