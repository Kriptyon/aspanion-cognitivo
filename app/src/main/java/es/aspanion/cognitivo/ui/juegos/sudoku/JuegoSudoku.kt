package es.aspanion.cognitivo.ui.juegos.sudoku

import android.media.AudioAttributes
import android.media.SoundPool
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.aspanion.cognitivo.R
import es.aspanion.cognitivo.ui.juegos.sudoku.model.CeldaSudoku

@Composable
fun JuegoSudoku(nivel: String, alVolver: () -> Unit) {
    val context = LocalContext.current
    val items = if (nivel == "experto") listOf("", "1", "2", "3", "4")
    else listOf("", "🦁", "🐘", "🦒", "🐼")

    // --- 1. CONFIGURACIÓN DE SONIDO ---
    val soundPool = remember {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        SoundPool.Builder().setMaxStreams(1).setAudioAttributes(attributes).build()
    }
    val idWin = remember { soundPool.load(context, R.raw.sonido_win, 1) }
    fun sonarWin() = soundPool.play(idWin, 1f, 1f, 1, 0, 1f)

    DisposableEffect(Unit) { onDispose { soundPool.release() } }

    // --- 2. LÓGICA DEL TABLERO ---
    val solucion = listOf(
        1, 2, 3, 4,
        3, 4, 1, 2,
        2, 3, 4, 1,
        4, 1, 2, 3
    )

    var tablero by remember {
        mutableStateOf(List(16) { i ->
            CeldaSudoku(fila = i / 4, columna = i % 4, valorCorrecto = solucion[i], valorActual = 0)
        })
    }

    var haGanado by remember { mutableStateOf(false) }

    fun verificarVictoria(nuevoTablero: List<CeldaSudoku>) {
        if (nuevoTablero.any { it.valorActual == 0 }) return

        // Forzamos el tipo a Int para evitar el error de mismatch
        fun tieneRepetidos(lista: List<Int>): Boolean {
            val filtrada = lista.filter { it != 0 }
            return filtrada.distinct().size != filtrada.size
        }

        var errorEncontrado = false

        for (f in 0 until 4) {
            // Usamos ?: 0 para asegurar que la lista sea de Int y no de Int?
            val fila = nuevoTablero.filter { it.fila == f }.map { it.valorActual ?: 0 }
            if (tieneRepetidos(fila)) errorEncontrado = true
        }

        for (c in 0 until 4) {
            val columna = nuevoTablero.filter { it.columna == c }.map { it.valorActual ?: 0 }
            if (tieneRepetidos(columna)) errorEncontrado = true
        }

        if (!errorEncontrado) {
            haGanado = true
            sonarWin()
        }
    }

    // --- 3. DISEÑO (RESTAURADO) ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botón de menú
        TextButton(onClick = alVolver, modifier = Modifier.align(Alignment.Start)) {
            Text("⬅ Menú", color = Color.Gray)
        }

        Text("Sudoku Animal", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE91E63))

        Spacer(modifier = Modifier.height(20.dp))

        // EL TABLERO
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
                                    .background(if (haGanado) Color(0xFFE8F5E9) else Color.White)
                                    .drawBehind {
                                        val grosorR = if (c == 1) 4.dp.toPx() else 1.dp.toPx()
                                        val colorR = if (c == 1) Color.Black else Color.LightGray
                                        if (c < 3) drawLine(colorR, Offset(size.width, 0f), Offset(size.width, size.height), grosorR)

                                        val grosorB = if (f == 1) 4.dp.toPx() else 1.dp.toPx()
                                        val colorB = if (f == 1) Color.Black else Color.LightGray
                                        if (f < 3) drawLine(colorB, Offset(0f, size.height), Offset(size.width, size.height), grosorB)
                                    }
                                    .clickable(!haGanado) {
                                        val t = tablero.toMutableList()
                                        val actual = t[idx].valorActual ?: 0
                                        t[idx] = t[idx].copy(valorActual = (actual + 1) % 5)
                                        tablero = t
                                        verificarVictoria(t)
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

        if (!haGanado) {
            Text("Pon los animales sin repetir en los cuadros", fontSize = 16.sp, color = Color.Gray)
        } else {
            Button(
                onClick = {
                    tablero = tablero.map { it.copy(valorActual = 0) }
                    haGanado = false
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
            ) {
                Text("Jugar otra vez")
            }
        }
    }
}