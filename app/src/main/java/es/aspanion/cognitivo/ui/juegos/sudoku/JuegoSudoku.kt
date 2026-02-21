package es.aspanion.cognitivo.ui.juegos.sudoku

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun JuegoSudoku() {

    val grid = remember {
        List(9) { fila ->
            List(9) { columna ->
                (fila + columna) % 9 + 1
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        grid.forEach { fila ->
            Row {
                fila.forEach { valor ->
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .border(1.dp, MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = valor.toString())
                    }
                }
            }
        }
    }
}