package es.aspanion.cognitivo.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
// IMPORTANTE: Asegúrate de que este import coincida con la ubicación de tu modelo
import es.aspanion.cognitivo.ui.juegos.pintar.model.PlantillaPintar
import es.aspanion.cognitivo.ui.inicio.PantallaInicioAspanion
import es.aspanion.cognitivo.ui.menu.PantallaMenuJuegos
import es.aspanion.cognitivo.ui.juegos.memory.JuegoMemory
import es.aspanion.cognitivo.ui.juegos.pintar.JuegoPintarNumeros
import es.aspanion.cognitivo.ui.juegos.pintar.PantallaSeleccionPlantilla
import es.aspanion.cognitivo.ui.juegos.sudoku.JuegoSudoku

@Composable
fun ControladorNavegacion() {
    var nivelGlobal by remember { mutableStateOf("") }
    var pantallaActual by remember { mutableStateOf("inicio") }
    var plantillaSeleccionada by remember { mutableStateOf<PlantillaPintar?>(null) }

    Surface(modifier = Modifier.fillMaxSize()) {
        when (pantallaActual) {
            "inicio" -> PantallaInicioAspanion { nivel ->
                nivelGlobal = nivel
                pantallaActual = "menu_juegos"
            }
            "menu_juegos" -> PantallaMenuJuegos(
                nivelGlobal,
                { juego -> pantallaActual = juego },
                { pantallaActual = "inicio" }
            )
            "memory" -> JuegoMemory(nivelGlobal) {
                pantallaActual = "menu_juegos"
            }
            "sudoku" -> JuegoSudoku(nivelGlobal) {
                pantallaActual = "menu_juegos"
            }
            "seleccion_plantilla" -> PantallaSeleccionPlantilla(nivelGlobal) { p ->
                plantillaSeleccionada = p
                pantallaActual = "pintar"
            }
            "pintar" -> JuegoPintarNumeros(plantillaSeleccionada!!) {
                pantallaActual = "menu_juegos"
            }
        }
    }
}