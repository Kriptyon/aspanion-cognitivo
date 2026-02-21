package es.aspanion.cognitivo.ui.juegos.memory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.aspanion.cognitivo.ui.juegos.memory.model.Carta

@Composable
fun JuegoMemory() {

    val cartas = remember {
        mutableStateListOf(
            Carta(1, "🐶"), Carta(1, "🐶"),
            Carta(2, "🐱"), Carta(2, "🐱")
        ).shuffled().toMutableStateList()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        cartas.chunked(2).forEach { fila ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                fila.forEach { carta ->
                    Card(
                        modifier = Modifier
                            .size(80.dp)
                            .clickable {
                                if (!carta.descubierta) {
                                    carta.descubierta = true
                                }
                            }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = if (carta.descubierta) carta.icono else "❓")
                        }
                    }
                }
            }
        }
    }
}