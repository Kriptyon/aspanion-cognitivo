package es.aspanion.cognitivo.navigation

import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Imports de tus pantallas
import es.aspanion.cognitivo.R
import es.aspanion.cognitivo.ui.inicio.PantallaInicioAspanion
import es.aspanion.cognitivo.ui.menu.PantallaMenuJuegos
import es.aspanion.cognitivo.ui.juegos.memory.JuegoMemory
import es.aspanion.cognitivo.ui.juegos.sudoku.JuegoSudoku
import es.aspanion.cognitivo.ui.juegos.sombras.JuegoSombras
import es.aspanion.cognitivo.ui.juegos.simon.JuegoSimon
import es.aspanion.cognitivo.ui.juegos.pintar.JuegoPintarNumeros
import es.aspanion.cognitivo.ui.juegos.pintar.PantallaSeleccionPlantilla
import es.aspanion.cognitivo.ui.juegos.pintar.model.PlantillaPintar

@Composable
fun ControladorNavegacion() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // --- MÚSICA Y ESTADOS ---
    var musicaActivada by remember { mutableStateOf(true) }

    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.musica_fondo).apply {
            isLooping = true
            setVolume(0.25f, 0.25f)
        }
    }

    // Gestión del Ciclo de Vida (Para que la música NO suene en la Home de Android)
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    mediaPlayer.pause()
                }
                Lifecycle.Event.ON_RESUME -> {
                    if (musicaActivada) {
                        mediaPlayer.start()
                    }
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mediaPlayer.release()
        }
    }

    // Reacción al botón manual
    LaunchedEffect(musicaActivada) {
        if (musicaActivada) mediaPlayer.start() else mediaPlayer.pause()
    }

    // --- NAVEGACIÓN ---
    var nivelGlobal by remember { mutableStateOf("") }
    var pantallaActual by remember { mutableStateOf("inicio") }
    var plantillaSeleccionada by remember { mutableStateOf<PlantillaPintar?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {

        Surface(modifier = Modifier.fillMaxSize()) {
            when (pantallaActual) {
                "inicio" -> PantallaInicioAspanion { nivel ->
                    nivelGlobal = nivel
                    pantallaActual = "menu_juegos"
                }
                "menu_juegos" -> PantallaMenuJuegos(
                    nivel = nivelGlobal,
                    alElegirJuego = { pantallaActual = it },
                    alCambiarEdad = { pantallaActual = "inicio" }
                )
                "memory" -> JuegoMemory(nivelGlobal) { pantallaActual = "menu_juegos" }
                "sudoku" -> JuegoSudoku(nivelGlobal) { pantallaActual = "menu_juegos" }
                "sombras" -> JuegoSombras(nivelGlobal) { pantallaActual = "menu_juegos" }
                "simon" -> JuegoSimon(nivelGlobal) { pantallaActual = "menu_juegos" }
                "seleccion_plantilla" -> PantallaSeleccionPlantilla(nivelGlobal) {
                    plantillaSeleccionada = it
                    pantallaActual = "pintar"
                }
                "pintar" -> JuegoPintarNumeros(plantillaSeleccionada!!) {
                    pantallaActual = "menu_juegos"
                }
            }
        }

        // --- BOTÓN MUSICAL (Corchea y Silencio de Negra) ---
        IconButton(
            onClick = { musicaActivada = !musicaActivada },
            modifier = Modifier
                .align(
                    when (pantallaActual) {
                        "menu_juegos" -> Alignment.TopCenter
                        else -> Alignment.TopEnd
                    }
                )
                .padding(
                    top = 16.dp,
                    end = if (pantallaActual != "menu_juegos") 16.dp else 0.dp
                )
                .size(65.dp)
        ) {
            Text(
                text = if (musicaActivada) "♪" else "𝄽",
                fontSize = if (musicaActivada) 45.sp else 35.sp,
                color = if (musicaActivada) Color(0xFFE91E63) else Color.Gray,
                fontWeight = FontWeight.Bold
            )
        }
    }
}