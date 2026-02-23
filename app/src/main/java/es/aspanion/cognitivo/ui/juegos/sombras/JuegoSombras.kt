package es.aspanion.cognitivo.ui.juegos.sombras

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.aspanion.cognitivo.R
import es.aspanion.cognitivo.ui.juegos.sombras.model.NivelSombras

@Composable
fun JuegoSombras(nivelDificultad: String, alVolver: () -> Unit) {

    // Lista de todos tus animales disponibles en drawable
    val bancoAnimales = remember {
        listOf(
            "🐥" to R.drawable.pollito_real,
            "🐸" to R.drawable.rana_real,
            "🦊" to R.drawable.zorro_real,
            "🐘" to R.drawable.elefante_real,
            "🐼" to R.drawable.panda_real,
            "🦁" to R.drawable.leon_real
        )
    }

    // Función que genera un nivel aleatorio mezclando las opciones
    fun generarNuevoNivel(): NivelSombras {
        val correcto = bancoAnimales.random()
        // Elegimos otros 2 animales al azar que no sean el correcto
        val incorrectos = bancoAnimales.filter { it.second != correcto.second }.shuffled().take(2)
        val opcionesMezcladas = (incorrectos + correcto).map { it.second }.shuffled()

        return NivelSombras(correcto.first, correcto.second, opcionesMezcladas)
    }

    // Estados: nivel actual y si ha acertado
    var nivelActual by remember { mutableStateOf(generarNuevoNivel()) }
    var haAcertado by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botón para salir cuando el niño quiera
        TextButton(onClick = alVolver, modifier = Modifier.align(Alignment.Start)) {
            Text("⬅ Ir a otro juego", fontSize = 16.sp, color = Color.Gray)
        }

        Text("¿De quién es esta sombra?", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(40.dp))

        // SECCIÓN DE LA SOMBRA (Silueta negra real)
        Box(
            modifier = Modifier
                .size(220.dp)
                .background(Color(0xFFF8F9FA), RoundedCornerShape(24.dp))
                .border(2.dp, Color(0xFFEEEEEE), RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nivelActual.emojiSombra,
                fontSize = 140.sp,
                modifier = Modifier
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithContent {
                        drawContent()
                        // Este comando "tapa" el emoji con negro puro siguiendo su forma
                        drawRect(color = Color.Black, blendMode = BlendMode.SrcAtop)
                    }
            )
        }

        Spacer(Modifier.height(50.dp))

        Text("Toca el animal de la sombra:", fontSize = 18.sp, color = Color.DarkGray)

        Spacer(Modifier.height(20.dp))

        // SECCIÓN DE OPCIONES (Fotos reales JPEG)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            nivelActual.opciones.forEach { imagenResId ->
                Card(
                    modifier = Modifier
                        .size(100.dp)
                        .border(
                            width = if (haAcertado && imagenResId == nivelActual.imagenReal) 4.dp else 0.dp,
                            color = Color(0xFF4CAF50), // Verde si acierta
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            if (!haAcertado && imagenResId == nivelActual.imagenReal) {
                                haAcertado = true
                            }
                        },
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Image(
                        painter = painterResource(imagenResId),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop // Para que la foto rellene el cuadro
                    )
                }
            }
        }

        // REFUERZO POSITIVO Y BOTÓN SIGUIENTE
        if (haAcertado) {
            Spacer(Modifier.height(30.dp))
            Text("¡LO HAS LOGRADO! ✨", fontSize = 28.sp, fontWeight = FontWeight.Black, color = Color(0xFFE91E63))

            Button(
                onClick = {
                    // GENERAMOS UN NIVEL NUEVO AL HACER CLICK
                    nivelActual = generarNuevoNivel()
                    haAcertado = false
                },
                modifier = Modifier.padding(top = 10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
            ) {
                Text("¡Siguiente animal! ➡", fontSize = 18.sp)
            }
        }
    }
}