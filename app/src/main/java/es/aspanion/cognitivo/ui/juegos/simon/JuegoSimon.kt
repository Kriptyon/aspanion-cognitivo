package es.aspanion.cognitivo.ui.juegos.simon

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.aspanion.cognitivo.ui.juegos.simon.model.BotonSimon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun JuegoSimon(nivelDificultad: String, alVolver: () -> Unit) {
    val botones = remember {
        listOf(
            BotonSimon("🐸", Color(0xFF4CAF50), Color(0xFF81C784)),
            BotonSimon("🐥", Color(0xFFFFEB3B), Color(0xFFFFF176)),
            BotonSimon("🦊", Color(0xFFFF9800), Color(0xFFFFB74D)),
            BotonSimon("🦁", Color(0xFFE91E63), Color(0xFFF06292))
        )
    }

    val scope = rememberCoroutineScope()
    var secuencia by remember { mutableStateOf(listOf<Int>()) }
    var indiceUsuario by remember { mutableStateOf(0) }
    var botonBrillando by remember { mutableStateOf<Int?>(null) }
    var estaMostrandoSecuencia by remember { mutableStateOf(false) }
    var gameOver by remember { mutableStateOf(false) }

    fun iniciarSecuencia() {
        scope.launch {
            estaMostrandoSecuencia = true
            delay(1000)
            for (index in secuencia) {
                botonBrillando = index
                // Velocidad según nivel
                delay(if (nivelDificultad == "experto") 450 else 750)
                botonBrillando = null
                delay(200)
            }
            estaMostrandoSecuencia = false
            indiceUsuario = 0
        }
    }

    fun nuevaRonda() {
        secuencia = secuencia + (0..3).random()
        iniciarSecuencia()
    }

    LaunchedEffect(Unit) { nuevaRonda() }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(onClick = alVolver, Modifier.align(Alignment.Start)) { Text("⬅ Menú") }

        Text("Simón Animal", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE91E63))
        Text("Puntuación: ${secuencia.size - 1}", fontSize = 20.sp, color = Color.Gray)

        Spacer(Modifier.height(30.dp))

        // CUADRÍCULA DE BOTONES
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                BotonUI(botones[0], botonBrillando == 0) {
                    if (!estaMostrandoSecuencia && !gameOver) {
                        if (secuencia[indiceUsuario] == 0) {
                            indiceUsuario++
                            if (indiceUsuario == secuencia.size) nuevaRonda()
                        } else { gameOver = true }
                    }
                }
                BotonUI(botones[1], botonBrillando == 1) {
                    if (!estaMostrandoSecuencia && !gameOver) {
                        if (secuencia[indiceUsuario] == 1) {
                            indiceUsuario++
                            if (indiceUsuario == secuencia.size) nuevaRonda()
                        } else { gameOver = true }
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                BotonUI(botones[2], botonBrillando == 2) {
                    if (!estaMostrandoSecuencia && !gameOver) {
                        if (secuencia[indiceUsuario] == 2) {
                            indiceUsuario++
                            if (indiceUsuario == secuencia.size) nuevaRonda()
                        } else { gameOver = true }
                    }
                }
                BotonUI(botones[3], botonBrillando == 3) {
                    if (!estaMostrandoSecuencia && !gameOver) {
                        if (secuencia[indiceUsuario] == 3) {
                            indiceUsuario++
                            if (indiceUsuario == secuencia.size) nuevaRonda()
                        } else { gameOver = true }
                    }
                }
            }
        }

        if (gameOver) {
            Spacer(Modifier.height(30.dp))
            Text("¡Vaya! Has llegado lejos 🎗️", fontWeight = FontWeight.Bold, color = Color.Red)
            Button(
                onClick = {
                    secuencia = emptyList()
                    gameOver = false
                    nuevaRonda()
                },
                modifier = Modifier.padding(top = 10.dp)
            ) { Text("Volver a intentar") }
        } else if (!estaMostrandoSecuencia) {
            Spacer(Modifier.height(30.dp))
            Text("¡Tu turno!", fontSize = 22.sp, fontWeight = FontWeight.Medium, color = Color(0xFF4CAF50))
        }
    }
}

@Composable
fun BotonUI(boton: BotonSimon, brilla: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(140.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(if (brilla) boton.colorBrillo else boton.colorBase)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(boton.emoji, fontSize = 65.sp)
    }
}