package es.aspanion.cognitivo.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
// Asegúrate de que estas rutas coincidan con tus carpetas
import es.aspanion.cognitivo.ui.juegos.pintar.model.PlantillaPintar
import es.aspanion.cognitivo.ui.inicio.PantallaInicioAspanion
import es.aspanion.cognitivo.ui.menu.PantallaMenuJuegos
import es.aspanion.cognitivo.ui.juegos.memory.JuegoMemory
import es.aspanion.cognitivo.ui.juegos.pintar.JuegoPintarNumeros
import es.aspanion.cognitivo.ui.juegos.pintar.PantallaSeleccionPlantilla
import es.aspanion.cognitivo.ui.juegos.sudoku.JuegoSudoku
import es.aspanion.cognitivo.ui.juegos.sombras.JuegoSombras

@Composable
fun ControladorNavegacion() {
    // Estados para saber en qué pantalla estamos y qué nivel eligió el niño
    var nivelGlobal by remember { mutableStateOf("") }
    var pantallaActual by remember { mutableStateOf("inicio") }
    var plantillaSeleccionada by remember { mutableStateOf<PlantillaPintar?>(null) }

    Surface(modifier = Modifier.fillMaxSize()) {
        when (pantallaActual) {
            // 1. Pantalla de Bienvenida
            "inicio" -> PantallaInicioAspanion { nivel ->
                nivelGlobal = nivel
                pantallaActual = "menu_juegos"
            }

            // 2. Menú principal de juegos
            "menu_juegos" -> PantallaMenuJuegos(
                nivel = nivelGlobal,
                alElegirJuego = { juego -> pantallaActual = juego },
                alCambiarEdad = { pantallaActual = "inicio" }
            )

            // 3. Juego de Memoria
            "memory" -> JuegoMemory(nivelGlobal) {
                pantallaActual = "menu_juegos"
            }

            // 4. Juego de Sudoku
            "sudoku" -> JuegoSudoku(nivelGlobal) {
                pantallaActual = "menu_juegos"
            }

            // 5. NUEVO: Juego de Sombras Chinas
            "sombras" -> JuegoSombras(nivelGlobal) { pantallaActual = "menu_juegos" }

            // 6. Selección de animal para pintar
            "seleccion_plantilla" -> PantallaSeleccionPlantilla(nivelGlobal) { plantilla ->
                plantillaSeleccionada = plantilla
                pantallaActual = "pintar"
            }

            // 7. Juego de Pintar por números
            "pintar" -> JuegoPintarNumeros(plantillaSeleccionada!!) {
                pantallaActual = "menu_juegos"
            }
        }
    }
}