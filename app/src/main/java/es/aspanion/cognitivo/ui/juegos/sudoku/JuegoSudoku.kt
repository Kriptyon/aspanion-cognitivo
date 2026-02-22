package es.aspanion.cognitivo.ui.juegos.sudoku

import androidx.compose.foundation.border // <-- Esta es la línea que faltaba
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.aspanion.cognitivo.ui.juegos.sudoku.model.CeldaSudoku

@Composable
fun JuegoSudoku(nivel: String, alVolver: () -> Unit) {
    val items = if (nivel == "experto") listOf("", "1", "2", "3", "4") else listOf("", "🦁", "🐘", "🦒", "🐼")
    var tablero by remember { mutableStateOf(List(16) { CeldaSudoku(it) }) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(onClick = alVolver) { Text("⬅ Volver") }
        Text("Sudoku Animal", fontSize = 24.sp, style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        // Cuadrícula de 4x4
        Column {
            for (f in 0 until 4) {
                Row {
                    for (c in 0 until 4) {
                        val idx = f * 4 + c
                        Box(
                            modifier = Modifier
                                .size(75.dp) // Un poco más grande para que quepan bien los animales
                                .border(1.dp, Color.Black)
                                .clickable {
                                    val t = tablero.toMutableList()
                                    t[idx] = t[idx].copy(valorIndex = (t[idx].valorIndex + 1) % 5)
                                    tablero = t
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(items[tablero[idx].valorIndex], fontSize = 34.sp)
                        }
                    }
                }
            }
        }
    }
}