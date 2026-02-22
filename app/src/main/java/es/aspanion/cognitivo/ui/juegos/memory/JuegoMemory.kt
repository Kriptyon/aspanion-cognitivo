package es.aspanion.cognitivo.ui.juegos.memory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.aspanion.cognitivo.ui.juegos.memory.model.Carta
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun JuegoMemory(nivel: String, alVolver: () -> Unit) {
    val iconos = if (nivel == "peque") listOf("🦁", "🐘", "🦁", "🐘")
    else listOf("🦁", "🐘", "🦒", "🐼", "🦊", "🐙", "🦁", "🐘", "🦒", "🐼", "🦊", "🐙")

    var cartas by remember { mutableStateOf(iconos.mapIndexed { i, icon -> Carta(i, icon) }.shuffled()) }
    var sel by remember { mutableStateOf(listOf<Int>()) }
    val scope = rememberCoroutineScope()
    val haGanado = cartas.all { it.estaVolteada }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextButton(onClick = alVolver) { Text("⬅ Volver") }
        if (haGanado) {
            Button(onClick = { cartas = iconos.mapIndexed { i, icon -> Carta(i, icon) }.shuffled(); sel = emptyList() }) {
                Text("¡Otra vez! 🔄")
            }
        }

        LazyVerticalGrid(columns = GridCells.Fixed(if(nivel=="peque") 2 else 4), modifier = Modifier.padding(top = 20.dp)) {
            items(cartas.size) { idx ->
                Card(modifier = Modifier.aspectRatio(1f).padding(4.dp).clickable {
                    if (sel.size < 2 && !cartas[idx].estaVolteada && !haGanado) {
                        val n = cartas.toMutableList(); n[idx] = n[idx].copy(estaVolteada = true); cartas = n
                        val nSel = sel + idx; sel = nSel
                        if (nSel.size == 2) {
                            scope.launch { delay(800)
                                if (cartas[nSel[0]].contenido != cartas[nSel[1]].contenido) {
                                    val r = cartas.toMutableList()
                                    r[nSel[0]] = r[nSel[0]].copy(estaVolteada = false); r[nSel[1]] = r[nSel[1]].copy(estaVolteada = false)
                                    cartas = r
                                }
                                sel = emptyList()
                            }
                        }
                    }
                }, colors = CardDefaults.cardColors(containerColor = if(cartas[idx].estaVolteada) Color.White else Color(0xFFC8E6C9))) {
                    Box(Modifier.fillMaxSize(), Alignment.Center) { Text(if(cartas[idx].estaVolteada) cartas[idx].contenido else "❓", fontSize = 30.sp) }
                }
            }
        }
    }
}