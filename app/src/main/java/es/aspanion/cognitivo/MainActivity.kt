package es.aspanion.cognitivo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.aspanion.cognitivo.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// --- COLORES Y ESTILOS ---
val RosaAspanion = Color(0xFFE91E63)
val GrisSuave = Color(0xFFF8F9FA)

// --- MODELOS DE DATOS ---
data class Carta(val id: Int, val contenido: String, var estaVolteada: Boolean = false)
data class CeldaSudoku(val id: Int, var valorIndex: Int = 0, var esError: Boolean = false)
data class CeldaPintar(val id: Int, val colorCorrecto: Int, var colorActual: Color = Color.White, var esError: Boolean = false, var estaPintado: Boolean = false)
data class PlantillaPintar(val nombre: String, val icono: String, val tam: Int, val dibujo: List<Int>, val colores: List<Color>, val imagenReal: Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AspanionTheme {
                ControladorNavegacion()
            }
        }
    }
}

@Composable
fun ControladorNavegacion() {
    var nivelGlobal by remember { mutableStateOf("") }
    var pantallaActual by remember { mutableStateOf("inicio") }
    var plantillaSeleccionada by remember { mutableStateOf<PlantillaPintar?>(null) }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        when (pantallaActual) {
            "inicio" -> PantallaInicioAspanion { nivel -> nivelGlobal = nivel; pantallaActual = "menu_juegos" }
            "menu_juegos" -> PantallaMenuJuegos(nivelGlobal, { pantallaActual = it }, { pantallaActual = "inicio" })
            "memory" -> JuegoMemory(nivelGlobal) { pantallaActual = "menu_juegos" }
            "sudoku" -> JuegoSudoku(nivelGlobal) { pantallaActual = "menu_juegos" }
            "seleccion_plantilla" -> PantallaSeleccionPlantilla(nivelGlobal) { p -> plantillaSeleccionada = p; pantallaActual = "pintar" }
            "pintar" -> JuegoPintarNumeros(plantillaSeleccionada!!) { pantallaActual = "menu_juegos" }
        }
    }
}

// --- CONFIGURACIÓN DE DIBUJOS ---
fun obtenerPlantillas(nivel: String): List<PlantillaPintar> {
    val naranjaZorro = Color(0xFFFF5722)
    val blancoHueso = Color(0xFFF5F5F5)
    val rosaIconos = Color(0xFFFF80AB)
    val grisElefante = Color(0xFF9E9E9E)
    val azulAgua = Color(0xFF2196F3)

    return when(nivel) {
        "peque" -> listOf(
            PlantillaPintar("Pollito", "🐥", 5, listOf(0,0,0,0,0, 0,1,1,0,0, 0,1,1,2,0, 0,1,1,0,0, 0,0,0,0,0), listOf(Color.White, Color.Yellow, Color(0xFFFF9800)), R.drawable.pollito_real),
            PlantillaPintar("Rana", "🐸", 5, listOf(1,0,1,0,1, 1,1,1,1,1, 1,2,2,2,1, 0,1,1,1,0, 1,0,1,0,1), listOf(Color.White, Color.Green, rosaIconos), R.drawable.rana_real)
        )
        "mediano" -> listOf(
            PlantillaPintar("Zorro", "🦊", 10, listOf(0,1,0,0,0,0,0,0,1,0, 0,1,1,0,0,0,0,1,1,0, 1,1,1,1,1,1,1,1,1,1, 1,1,1,1,1,1,1,1,1,1, 1,1,3,1,1,1,1,3,1,1, 1,2,2,2,2,2,2,2,2,1, 0,1,2,2,2,2,2,2,1,0, 0,0,1,2,2,2,2,1,0,0, 0,0,0,1,2,2,1,0,0,0, 0,0,0,0,1,1,0,0,0,0), listOf(Color.White, naranjaZorro, blancoHueso, Color.Black), R.drawable.zorro_real),
            PlantillaPintar("Elefante", "🐘", 10, listOf(0,0,0,2,0,0,0,0,0,0, 0,0,2,0,0,0,0,0,0,0, 0,1,1,1,1,1,0,0,0,0, 1,1,3,3,1,1,1,0,0,0, 1,3,3,3,1,1,1,1,0,0, 1,1,3,3,1,1,1,1,1,0, 0,1,1,1,1,1,1,1,1,1, 0,1,1,1,1,1,1,0,0,0, 0,1,0,1,0,1,0,0,0,0, 0,0,0,0,0,0,0,0,0,0), listOf(Color.White, grisElefante, azulAgua, rosaIconos), R.drawable.elefante_real),
            PlantillaPintar("Oso Panda", "🐼", 10, listOf(0,1,1,0,0,0,0,1,1,0, 1,1,1,1,0,0,1,1,1,1, 1,1,1,1,1,1,1,1,1,1, 1,3,3,1,1,1,1,3,3,1, 1,3,3,1,1,1,1,3,3,1, 1,1,1,2,2,2,2,1,1,1, 1,1,2,2,2,2,2,2,1,1, 0,1,2,2,2,2,2,2,1,0, 0,0,1,1,1,1,1,1,0,0, 0,0,0,0,0,0,0,0,0,0), listOf(Color.White, Color.Black, Color(0xFFEEEEEE), Color(0xFF424242)), R.drawable.panda_real)
        )
        else -> listOf(
            // LEÓN DETALLADO PARA MAYORES (5 colores + fondo)
            PlantillaPintar("León", "🦁", 12, listOf(
                0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0,
                0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0,
                2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2,
                2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2,
                2, 1, 1, 3, 1, 1, 1, 1, 3, 1, 1, 2,
                2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
                2, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 2,
                2, 1, 1, 1, 4, 3, 3, 4, 1, 1, 1, 2,
                2, 2, 1, 1, 4, 4, 4, 4, 1, 1, 2, 2,
                0, 2, 2, 1, 1, 5, 5, 1, 1, 2, 2, 0,
                0, 0, 2, 2, 1, 1, 1, 1, 2, 2, 0, 0,
                0, 0, 0, 2, 2, 2, 2, 2, 2, 0, 0, 0
            ), listOf(Color.White, Color(0xFFFFC107), Color(0xFF795548), Color.Black, Color(0xFFFFF9C4), Color(0xFFFF80AB)), R.drawable.leon_real)
        )
    }
}

// --- PANTALLA INICIO ---
@Composable
fun PantallaInicioAspanion(onEdadElegida: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
        Image(painter = painterResource(id = R.drawable.logo_aspanion), contentDescription = null, modifier = Modifier.height(60.dp).fillMaxWidth())
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.martin_y_lola), contentDescription = null, modifier = Modifier.height(280.dp).fillMaxWidth())
            Text("¡Hola, Valiente!", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = RosaAspanion)
        }
        Column(modifier = Modifier.padding(bottom = 20.dp)) {
            BotonMenu("Peques (2-4)", Color(0xFFC8E6C9)) { onEdadElegida("peque") }
            Spacer(modifier = Modifier.height(10.dp))
            BotonMenu("Medianos (5-7)", Color(0xFFFFF9C4)) { onEdadElegida("mediano") }
            Spacer(modifier = Modifier.height(10.dp))
            BotonMenu("Mayores (+8)", Color(0xFFFFE0B2)) { onEdadElegida("experto") }
        }
    }
}

// --- MENÚ JUEGOS ---
@Composable
fun PantallaMenuJuegos(nivel: String, alElegirJuego: (String) -> Unit, alCambiarEdad: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(GrisSuave).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextButton(onClick = alCambiarEdad, modifier = Modifier.align(Alignment.End)) { Text("⚙️ Edad") }
        Text("¿A qué jugamos?", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(40.dp))
        BotonMenu("Memory 🧠", Color(0xFFC8E6C9)) { alElegirJuego("memory") }
        Spacer(modifier = Modifier.height(16.dp))
        BotonMenu("Pintar Animales 🎨", Color(0xFFFFE0B2)) { alElegirJuego("seleccion_plantilla") }
        if (nivel != "peque") {
            Spacer(modifier = Modifier.height(16.dp))
            BotonMenu("Sudoku 🦁", Color(0xFFFFF9C4)) { alElegirJuego("sudoku") }
        }
    }
}

// --- JUEGO: PINTAR POR NÚMEROS ---
@Composable
fun JuegoPintarNumeros(plantilla: PlantillaPintar, alVolver: () -> Unit) {
    var lienzo by remember { mutableStateOf(plantilla.dibujo.mapIndexed { idx, col -> CeldaPintar(idx, col) }) }
    var colorSelIdx by remember { mutableStateOf(1) }
    var haGanado by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = alVolver) { Text("⬅ Menú") }
            if (haGanado) Text("¡MAGIA! ✨", color = RosaAspanion, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
        }

        Text(plantilla.nombre, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))

        if (!haGanado) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(bottom = 15.dp)) {
                for (i in 1 until plantilla.colores.size) {
                    Box(modifier = Modifier.size(50.dp).clip(CircleShape).background(plantilla.colores[i]).border(if (colorSelIdx == i) 4.dp else 1.dp, Color.Black, CircleShape).clickable { colorSelIdx = i }, contentAlignment = Alignment.Center) {
                        Text("$i", fontWeight = FontWeight.Bold, color = if(plantilla.colores[i] == Color.Black) Color.White else Color.Black)
                    }
                }
            }
        }

        Box(modifier = Modifier.weight(1f).aspectRatio(1f).clip(RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
            if (haGanado) {
                Image(painter = painterResource(id = plantilla.imagenReal), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Fit)
            } else {
                LazyVerticalGrid(columns = GridCells.Fixed(plantilla.tam), modifier = Modifier.fillMaxSize().border(2.dp, Color.Black), userScrollEnabled = false) {
                    items(lienzo.size) { idx ->
                        val celda = lienzo[idx]
                        Box(modifier = Modifier.aspectRatio(1f).border(0.1.dp, Color.LightGray).background(if (celda.esError) Color(0xFFFFCDD2) else celda.colorActual).clickable {
                            if (!haGanado && celda.colorCorrecto != 0) {
                                val n = lienzo.toMutableList()
                                if (colorSelIdx == celda.colorCorrecto) { n[idx] = celda.copy(colorActual = plantilla.colores[celda.colorCorrecto], estaPintado = true, esError = false) }
                                else { n[idx] = celda.copy(esError = true) }
                                lienzo = n
                                if (lienzo.all { it.colorCorrecto == 0 || it.estaPintado }) haGanado = true
                            }
                        }, contentAlignment = Alignment.Center) {
                            if (!celda.estaPintado && celda.colorCorrecto != 0) {
                                Text("${celda.colorCorrecto}", fontSize = (220/plantilla.tam).sp, color = Color.Gray.copy(alpha = 0.5f))
                            }
                        }
                    }
                }
            }
        }
        if(haGanado) {
            Button(onClick = { haGanado = false; lienzo = plantilla.dibujo.mapIndexed { i, c -> CeldaPintar(i, c) } }, modifier = Modifier.padding(top = 10.dp)) { Text("Repetir") }
        }
    }
}

// --- JUEGO: MEMORY ---
@Composable
fun JuegoMemory(nivel: String, alVolver: () -> Unit) {
    val iconos = if (nivel == "peque") listOf("🦁", "🐘", "🦁", "🐘") else if (nivel == "mediano") listOf("🦁", "🐘", "🦒", "🐼", "🦊", "🐙", "🦁", "🐘", "🦒", "🐼", "🦊", "🐙") else List(20) { "🐾" }
    var cartas by remember { mutableStateOf(iconos.mapIndexed { i, icon -> Carta(i, icon) }.shuffled()) }
    var sel by remember { mutableStateOf(listOf<Int>()) }
    val scope = rememberCoroutineScope()
    val haGanado = cartas.all { it.estaVolteada }

    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextButton(onClick = alVolver) { Text("⬅ Volver") }
        Text("Memory", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        if (haGanado) {
            Text("¡CRACK! 🏆", color = RosaAspanion, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Button(onClick = { cartas = iconos.mapIndexed { i, icon -> Carta(i, icon) }.shuffled(); sel = emptyList() }) { Text("¡Otra vez! 🔄") }
        }

        Spacer(modifier = Modifier.height(20.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(if(nivel=="peque") 2 else 4)) {
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

// --- JUEGO: SUDOKU ---
@Composable
fun JuegoSudoku(nivel: String, alVolver: () -> Unit) {
    val items = if (nivel == "experto") listOf("", "1", "2", "3", "4") else listOf("", "🦁", "🐘", "🦒", "🐼")
    var tablero by remember { mutableStateOf(List(16) { CeldaSudoku(it) }) }
    var haGanado by remember { mutableStateOf(false) }

    fun actualizarErrores(lista: List<CeldaSudoku>): List<CeldaSudoku> {
        val n = lista.map { it.copy(esError = false) }.toMutableList()
        for (i in 0 until 16) {
            if (n[i].valorIndex == 0) continue
            for (j in 0 until 16) {
                if (i == j) continue
                if (n[i].valorIndex == n[j].valorIndex && (i/4 == j/4 || i%4 == j%4 || ((i/4/2)*2 == (j/4/2)*2 && (i%4/2)*2 == (j%4/2)*2))) n[i] = n[i].copy(esError = true)
            }
        }
        return n
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextButton(onClick = alVolver) { Text("⬅ Volver") }
        Text("Sudoku", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        if (haGanado) Text("¡SÚPER! 🏆", color = RosaAspanion, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.background(Color.White).drawBehind {
            val p = size.width / 4
            for (i in 0..4) {
                val g = (i == 0 || i == 4 || i == 2)
                drawLine(Color.Black, Offset(i*p, 0f), Offset(i*p, size.height), if(g) 5.dp.toPx() else 1.dp.toPx())
                drawLine(Color.Black, Offset(0f, i*p), Offset(size.width, i*p), if(g) 5.dp.toPx() else 1.dp.toPx())
            }
        }) {
            Column { for (f in 0 until 4) { Row { for (c in 0 until 4) {
                val idx = f * 4 + c
                Box(modifier = Modifier.size(85.dp).background(if (tablero[idx].esError) Color(0xFFFFCDD2) else Color.Transparent).clickable {
                    val t = tablero.toMutableList()
                    t[idx] = t[idx].copy(valorIndex = (t[idx].valorIndex + 1) % 5)
                    tablero = actualizarErrores(t)
                    if (tablero.none { it.valorIndex == 0 || it.esError }) haGanado = true
                }, contentAlignment = Alignment.Center) { Text(items[tablero[idx].valorIndex], fontSize = 36.sp) }
            } } } }
        }
    }
}

// --- COMPONENTES AUXILIARES ---
@Composable
fun PantallaSeleccionPlantilla(nivel: String, alElegir: (PlantillaPintar) -> Unit) {
    val plantillas = obtenerPlantillas(nivel)
    Column(modifier = Modifier.fillMaxSize().background(GrisSuave).padding(24.dp)) {
        Text("Elige un animal", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        plantillas.forEach { p ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { alElegir(p) }) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(p.icono, fontSize = 40.sp); Spacer(modifier = Modifier.width(20.dp)); Text(p.nombre, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun BotonMenu(texto: String, color: Color, alClick: () -> Unit) {
    Button(onClick = alClick, modifier = Modifier.fillMaxWidth().height(60.dp), shape = RoundedCornerShape(18.dp), colors = ButtonDefaults.buttonColors(containerColor = color), elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)) {
        Text(texto, fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold)
    }
}