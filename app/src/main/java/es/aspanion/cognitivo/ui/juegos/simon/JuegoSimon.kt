package es.aspanion.cognitivo.ui.juegos.simon

import android.media.AudioAttributes
import android.media.SoundPool
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.aspanion.cognitivo.R
import es.aspanion.cognitivo.ui.juegos.simon.model.BotonSimon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun JuegoSimon(nivelDificultad: String, alVolver: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // --- 1. CONFIGURACIÓN DE SONIDOS (SoundPool) ---
    val soundPool = remember {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        SoundPool.Builder().setMaxStreams(5).setAudioAttributes(attributes).build()
    }

    // Cargamos tus archivos específicos
    val sonidosIds = remember {
        mutableMapOf<Int, Int>().apply {
            put(0, soundPool.load(context, R.raw.green, 1))
            put(1, soundPool.load(context, R.raw.yellow, 1))
            put(2, soundPool.load(context, R.raw.red, 1))
            put(3, soundPool.load(context, R.raw.blue, 1))
            put(99, soundPool.load(context, R.raw.wrong, 1)) // Sonido de error
        }
    }

    fun sonar(index: Int) {
        sonidosIds[index]?.let { id ->
            soundPool.play(id, 1f, 1f, 1, 0, 1f)
        }
    }

    // Liberar memoria al cerrar el juego
    DisposableEffect(Unit) {
        onDispose { soundPool.release() }
    }

    // --- 2. DEFINICIÓN DE BOTONES ---
    val botones = remember {
        listOf(
            BotonSimon("🐸", Color(0xFF1B5E20), Color(0xFF4CAF50)), // Verde -> green.mp3
            BotonSimon("🐥", Color(0xFFF9A825), Color(0xFFFFEB3B)), // Amarillo -> yellow.mp3
            BotonSimon("🦊", Color(0xFFB71C1C), Color(0xFFF44336)), // Rojo -> red.mp3
            BotonSimon("🦁", Color(0xFF0D47A1), Color(0xFF2196F3))  // Azul -> blue.mp3
        )
    }

    // --- 3. ESTADOS DEL JUEGO ---
    var secuencia by remember { mutableStateOf(listOf<Int>()) }
    var indiceUsuario by remember { mutableStateOf(0) }
    var botonBrillando by remember { mutableStateOf<Int?>(null) }
    var estaMostrandoSecuencia by remember { mutableStateOf(false) }
    var gameOver by remember { mutableStateOf(false) }

    // Función para mostrar la secuencia al niño
    fun iniciarSecuencia() {
        scope.launch {
            estaMostrandoSecuencia = true
            delay(1000)
            for (index in secuencia) {
                botonBrillando = index
                sonar(index) // Suena el color correspondiente
                delay(if (nivelDificultad == "experto") 400 else 700)
                botonBrillando = null
                delay(250)
            }
            estaMostrandoSecuencia = false
            indiceUsuario = 0
        }
    }

    fun nuevaRonda() {
        secuencia = secuencia + (0..3).random()
        iniciarSecuencia()
    }

    // Feedback visual y sonoro cuando el usuario pulsa
    fun feedbackPulsacion(index: Int) {
        scope.launch {
            botonBrillando = index
            sonar(index) // Suena al tocar
            delay(150)
            botonBrillando = null
        }
    }

    LaunchedEffect(Unit) { nuevaRonda() }

    // --- 4. DISEÑO DE LA PANTALLA ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFDFD))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cabecera
        TextButton(onClick = alVolver, Modifier.align(Alignment.Start)) {
            Text("⬅ Menú", color = Color.Gray, fontSize = 18.sp)
        }

        Text("Simón de los Valientes", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE91E63))
        Text("Puntuación: ${if (secuencia.isEmpty()) 0 else secuencia.size - 1}", fontSize = 20.sp, color = Color.Gray)

        Spacer(Modifier.height(40.dp))

        // CUADRÍCULA DE BOTONES (2x2)
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            for (fila in 0..1) {
                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    for (columna in 0..1) {
                        val index = fila * 2 + columna
                        BotonUI(
                            boton = botones[index],
                            brilla = botonBrillando == index
                        ) {
                            if (!estaMostrandoSecuencia && !gameOver) {
                                feedbackPulsacion(index)

                                if (secuencia[indiceUsuario] == index) {
                                    indiceUsuario++
                                    if (indiceUsuario == secuencia.size) {
                                        scope.launch {
                                            delay(600)
                                            nuevaRonda()
                                        }
                                    }
                                } else {
                                    sonar(99) // Suena wrong.mp3
                                    gameOver = true
                                }
                            }
                        }
                    }
                }
            }
        }

        // Mensajes de estado y Game Over
        if (gameOver) {
            Spacer(Modifier.height(40.dp))
            Text("¡Has llegado lejos! 🎗️", fontWeight = FontWeight.Bold, color = Color.Red, fontSize = 20.sp)
            Button(
                onClick = {
                    secuencia = emptyList()
                    gameOver = false
                    nuevaRonda()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
                modifier = Modifier.padding(top = 15.dp).height(55.dp).fillMaxWidth(0.7f),
                shape = RoundedCornerShape(15.dp)
            ) { Text("REINTENTAR", color = Color.White, fontWeight = FontWeight.Bold) }
        } else {
            Spacer(Modifier.height(40.dp))
            val textoTurno = if (estaMostrandoSecuencia) "¡Mira con atención! 👀" else "¡Tu turno! 👇"
            val colorTurno = if (estaMostrandoSecuencia) Color.Gray else Color(0xFF4CAF50)
            Text(textoTurno, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = colorTurno)
        }
    }
}

@Composable
fun BotonUI(boton: BotonSimon, brilla: Boolean, onClick: () -> Unit) {
    val escala by animateFloatAsState(if (brilla) 1.2f else 1.0f)

    val capaOscura by animateColorAsState(
        if (brilla) Color.Transparent else Color.Black.copy(alpha = 0.75f)
    )

    Box(
        modifier = Modifier
            .size(145.dp)
            .graphicsLayer(scaleX = escala, scaleY = escala)
            .clip(RoundedCornerShape(32.dp))
            .background(boton.colorBase)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(boton.emoji, fontSize = 75.sp)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(capaOscura)
        )

        if (brilla) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Transparent,
                shape = RoundedCornerShape(32.dp),
                border = BorderStroke(6.dp, Color.White.copy(alpha = 0.9f))
            ) {}
        }
    }
}