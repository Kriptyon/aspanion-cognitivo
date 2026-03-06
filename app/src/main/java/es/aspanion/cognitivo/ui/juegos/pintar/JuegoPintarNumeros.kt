package es.aspanion.cognitivo.ui.juegos.pintar

import android.media.AudioAttributes
import android.media.SoundPool
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.aspanion.cognitivo.R
import es.aspanion.cognitivo.ui.juegos.pintar.model.PlantillaPintar
import es.aspanion.cognitivo.ui.juegos.pintar.model.CeldaPintar
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun JuegoPintarNumeros(plantilla: PlantillaPintar, alVolver: () -> Unit) {
    val context = LocalContext.current

    // --- 1. CONFIGURACIÓN DE SONIDO (Solo Win) ---
    val soundPool = remember {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        SoundPool.Builder().setMaxStreams(1).setAudioAttributes(attributes).build()
    }

    val idWin = remember { soundPool.load(context, R.raw.sonido_win, 1) }

    fun sonarWin() = soundPool.play(idWin, 1f, 1f, 1, 0, 1f)

    DisposableEffect(Unit) {
        onDispose { soundPool.release() }
    }

    // --- 2. ESTADOS DEL JUEGO ---
    var lienzo by remember { mutableStateOf(plantilla.dibujo.mapIndexed { idx, col -> CeldaPintar(idx, col) }) }
    var colorSelIdx by remember { mutableStateOf(1) }
    var haGanado by remember { mutableStateOf(false) }

    // Disparar sonido cuando gane
    LaunchedEffect(haGanado) {
        if (haGanado) {
            sonarWin()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        // CABECERA
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = alVolver) { Text("⬅ Menú", color = Color.Gray) }
            if (haGanado) {
                Text("¡MAGIA! ✨", color = Color(0xFFE91E63), fontWeight = FontWeight.Bold, fontSize = 24.sp)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // PALETA DE COLORES CON NÚMEROS
        if (!haGanado) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.horizontalScroll(rememberScrollState())) {
                for (i in 1 until plantilla.colores.size) {
                    val colorDeFondo = plantilla.colores[i]

                    Box(
                        modifier = Modifier
                            .size(55.dp)
                            .clip(CircleShape)
                            .background(colorDeFondo)
                            .border(if (colorSelIdx == i) 4.dp else 1.dp, Color.Black, CircleShape)
                            .clickable { colorSelIdx = i },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$i",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = if (colorDeFondo == Color.Black) Color.White else Color.Black
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // LIENZO DE DIBUJO
        Box(modifier = Modifier.weight(1f).aspectRatio(1f)) {
            if (haGanado) {
                // Imagen final revelada
                Image(
                    painter = painterResource(id = plantilla.imagenReal),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(16.dp))
                )
            } else {
                LazyVerticalGrid(columns = GridCells.Fixed(plantilla.tam), userScrollEnabled = false) {
                    items(lienzo.size) { idx ->
                        val celda = lienzo[idx]
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .border(0.2.dp, Color.LightGray.copy(alpha = 0.5f))
                                .background(if (celda.esError) Color.Red.copy(0.4f) else celda.colorActual)
                                .clickable {
                                    if (!haGanado && celda.colorCorrecto != 0) {
                                        val n = lienzo.toMutableList()
                                        if (colorSelIdx == celda.colorCorrecto) {
                                            // Acierto: Pintamos
                                            n[idx] = celda.copy(
                                                colorActual = plantilla.colores[celda.colorCorrecto],
                                                estaPintado = true,
                                                esError = false
                                            )
                                        } else {
                                            // Error: Solo visual (sin sonido)
                                            n[idx] = celda.copy(esError = true)
                                        }
                                        lienzo = n
                                        // Comprobación de victoria
                                        if (lienzo.all { it.colorCorrecto == 0 || it.estaPintado }) {
                                            haGanado = true
                                        }
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (!celda.estaPintado && celda.colorCorrecto != 0) {
                                Text(
                                    text = "${celda.colorCorrecto}",
                                    fontSize = (200 / plantilla.tam).sp,
                                    color = Color.Gray.copy(alpha = 0.5f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}