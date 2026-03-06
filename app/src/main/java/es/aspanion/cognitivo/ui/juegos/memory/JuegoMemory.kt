package es.aspanion.cognitivo.ui.juegos.memory

import android.media.AudioAttributes
import android.media.SoundPool
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.aspanion.cognitivo.R
import es.aspanion.cognitivo.ui.juegos.memory.model.Carta
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun JuegoMemory(nivel: String, alVolver: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // --- 1. CONFIGURACIÓN DE SONIDOS (Solo Éxito) ---
    val soundPool = remember {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        // Ponemos 1 solo stream porque solo nos interesa el sonido de victoria
        SoundPool.Builder().setMaxStreams(1).setAudioAttributes(attributes).build()
    }

    val idWin = remember { soundPool.load(context, R.raw.sonido_win, 1) }

    fun sonarWin() = soundPool.play(idWin, 1f, 1f, 1, 0, 1f)

    // Liberar SoundPool al salir
    DisposableEffect(Unit) {
        onDispose { soundPool.release() }
    }

    // --- 2. LÓGICA DEL JUEGO ---
    val iconos = remember(nivel) {
        if (nivel == "peque") listOf("🦁", "🐘", "🦁", "🐘")
        else listOf("🦁", "🐘", "🦒", "🐼", "🦊", "🐙", "🦁", "🐘", "🦒", "🐼", "🦊", "🐙")
    }

    var cartas by remember { mutableStateOf(iconos.mapIndexed { i, icon -> Carta(i, icon) }.shuffled()) }
    var sel by remember { mutableStateOf(listOf<Int>()) }

    // El estado de victoria
    val haGanado = remember(cartas) { cartas.all { it.estaVolteada } }

    // Lanzar sonido de victoria solo una vez cuando gane
    LaunchedEffect(haGanado) {
        if (haGanado && cartas.isNotEmpty()) {
            sonarWin()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cabecera
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            TextButton(onClick = alVolver) { Text("⬅ Volver", color = Color.Gray) }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (haGanado) {
            Text("¡LO HAS LOGRADO! 🎗️", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
            Button(
                onClick = {
                    cartas = iconos.mapIndexed { i, icon -> Carta(i, icon) }.shuffled()
                    sel = emptyList()
                },
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text("¿Jugamos otra vez? 🔄")
            }
        } else {
            Text("Busca las parejas", fontSize = 20.sp, color = Color.DarkGray)
        }



        LazyVerticalGrid(
            columns = GridCells.Fixed(if(nivel == "peque") 2 else 4),
            modifier = Modifier.padding(top = 20.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(cartas.size) { idx ->
                Card(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(6.dp)
                        .clickable {
                            if (sel.size < 2 && !cartas[idx].estaVolteada && !haGanado) {
                                // Voltear carta seleccionada
                                val nuevasCartas = cartas.toMutableList()
                                nuevasCartas[idx] = nuevasCartas[idx].copy(estaVolteada = true)
                                cartas = nuevasCartas

                                val nSel = sel + idx
                                sel = nSel

                                if (nSel.size == 2) {
                                    scope.launch {
                                        delay(800)
                                        // Comprobar si son iguales
                                        if (cartas[nSel[0]].contenido != cartas[nSel[1]].contenido) {
                                            // NO SON IGUALES: Solo volteamos atrás (sin sonido)
                                            val r = cartas.toMutableList()
                                            r[nSel[0]] = r[nSel[0]].copy(estaVolteada = false)
                                            r[nSel[1]] = r[nSel[1]].copy(estaVolteada = false)
                                            cartas = r
                                        }
                                        sel = emptyList()
                                    }
                                }
                            }
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if(cartas[idx].estaVolteada) Color.White else Color(0xFFC8E6C9)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        Text(
                            text = if(cartas[idx].estaVolteada) cartas[idx].contenido else "❓",
                            fontSize = 40.sp
                        )
                    }
                }
            }
        }
    }
}