package es.aspanion.cognitivo.ui.juegos.sudoku.model

data class CeldaSudoku(
    val fila: Int,
    val columna: Int,
    val valorCorrecto: Int,
    var valorActual: Int? = null
)