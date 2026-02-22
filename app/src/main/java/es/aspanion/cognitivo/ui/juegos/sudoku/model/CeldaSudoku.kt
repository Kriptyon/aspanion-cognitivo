package es.aspanion.cognitivo.ui.juegos.sudoku.model

data class CeldaSudoku(
    val id: Int,
    var valorIndex: Int = 0,
    var esError: Boolean = false
)