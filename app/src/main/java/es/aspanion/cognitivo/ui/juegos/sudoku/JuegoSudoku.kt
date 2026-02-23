package es.aspanion.cognitivo.ui.juegos.sudoku

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.aspanion.cognitivo.ui.juegos.sudoku.model.CeldaSudoku

@Composable
fun JuegoSudoku(nivel: String, alVolver: () -> Unit) {
    val items = if (nivel == "experto") listOf("", "1", "2", "3", "4")
    else listOf("", "🦁", "🐘", "🦒", "🐼")

    // Tablero de 4x4
    var tablero by remember {
        mutableStateOf(List(16) { i ->
            CeldaSudoku(fila = i / 4, columna = i % 4, valorCorrecto = 0, valorActual = 0)
        })
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(onClick = alVolver, modifier = Modifier.align(Alignment.Start)) {
            Text("⬅ Menú")
        }

        Text("Sudoku Animal", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE91E63))

        Spacer(modifier = Modifier.height(20.dp))

        // Contenedor con borde negro para cerrar el dibujo por fuera
        Box(modifier = Modifier.border(2.dp, Color.Black)) {
            Column {
                for (f in 0 until 4) {
                    Row {
                        for (c in 0 until 4) {
                            val idx = f * 4 + c
                            val celda = tablero[idx]

                            Box(
                                modifier = Modifier
                                    .size(75.dp)
                                    .background(Color.White)
                                    .drawBehind {
                                        // Dibujamos el borde derecho de cada celda
                                        val grosorR = if (c == 1) 4.dp.toPx() else 1.dp.toPx()
                                        val colorR = if (c == 1) Color.Black else Color.LightGray
                                        if (c < 3) { // No dibujamos en la última columna porque ya tiene el borde del Box
                                            drawLine(colorR, Offset(size.width, 0f), Offset(size.width, size.height), grosorR)
                                        }

                                        // Dibujamos el borde inferior de cada celda
                                        val grosorB = if (f == 1) 4.dp.toPx() else 1.dp.toPx()
                                        val colorB = if (f == 1) Color.Black else Color.LightGray
                                        if (f < 3) { // No dibujamos en la última fila
                                            drawLine(colorB, Offset(0f, size.height), Offset(size.width, size.height), grosorB)
                                        }
                                    }
                                    .clickable {
                                        val t = tablero.toMutableList()
                                        val actual = t[idx].valorActual ?: 0
                                        t[idx] = t[idx].copy(valorActual = (actual + 1) % 5)
                                        tablero = t
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = items[celda.valorActual ?: 0], fontSize = 35.sp)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        Text("Pon los animales sin repetir en los cuadros", fontSize = 14.sp, color = Color.Gray)
    }
}