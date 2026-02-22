package es.aspanion.cognitivo.ui.juegos.pintar

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.aspanion.cognitivo.ui.juegos.pintar.model.PlantillaPintar
import es.aspanion.cognitivo.ui.juegos.pintar.model.CeldaPintar

@Composable
fun JuegoPintarNumeros(plantilla: PlantillaPintar, alVolver: () -> Unit) {
    var lienzo by remember { mutableStateOf(plantilla.dibujo.mapIndexed { idx, col -> CeldaPintar(idx, col) }) }
    var colorSelIdx by remember { mutableStateOf(1) }
    var haGanado by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = alVolver) { Text("⬅ Menú") }
            if (haGanado) Text("¡MAGIA! ✨", color = Color(0xFFE91E63), fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // PALETA DE COLORES CON NÚMEROS
        if (!haGanado) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                for (i in 1 until plantilla.colores.size) {
                    val colorDeFondo = plantilla.colores[i]

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(colorDeFondo)
                            .border(if (colorSelIdx == i) 4.dp else 1.dp, Color.Black, CircleShape)
                            .clickable { colorSelIdx = i },
                        contentAlignment = Alignment.Center // Para centrar el número
                    ) {
                        Text(
                            text = "$i",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            // Si el color es negro, ponemos el número blanco para que se vea
                            color = if (colorDeFondo == Color.Black) Color.White else Color.Black
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // DIBUJO
        Box(modifier = Modifier.weight(1f).aspectRatio(1f)) {
            if (haGanado) {
                Image(painter = painterResource(id = plantilla.imagenReal), contentDescription = null, modifier = Modifier.fillMaxSize())
            } else {
                LazyVerticalGrid(columns = GridCells.Fixed(plantilla.tam), userScrollEnabled = false) {
                    items(lienzo.size) { idx ->
                        val celda = lienzo[idx]
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .border(0.2.dp, Color.LightGray)
                                .background(if (celda.esError) Color.Red.copy(0.3f) else celda.colorActual)
                                .clickable {
                                    if (!haGanado && celda.colorCorrecto != 0) {
                                        val n = lienzo.toMutableList()
                                        if (colorSelIdx == celda.colorCorrecto) {
                                            n[idx] = celda.copy(colorActual = plantilla.colores[celda.colorCorrecto], estaPintado = true, esError = false)
                                        } else {
                                            n[idx] = celda.copy(esError = true)
                                        }
                                        lienzo = n
                                        if (lienzo.all { it.colorCorrecto == 0 || it.estaPintado }) haGanado = true
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (!celda.estaPintado && celda.colorCorrecto != 0) {
                                Text(
                                    text = "${celda.colorCorrecto}",
                                    fontSize = (200 / plantilla.tam).sp,
                                    color = Color.Gray.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}