package es.aspanion.cognitivo.ui.juegos.sudoku.model

data class CeldaSudoku(
    val fila: Int,
    val columna: Int,
    val valorCorrecto: Int = 0,
    var valorActual: Int? = 0
)