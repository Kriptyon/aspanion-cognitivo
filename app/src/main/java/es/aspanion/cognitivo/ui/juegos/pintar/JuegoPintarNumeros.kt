package es.aspanion.cognitivo.ui.juegos.pintar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import es.aspanion.cognitivo.ui.juegos.pintar.model.PlantillaPintar

@Composable
fun JuegoPintarNumeros(plantilla: PlantillaPintar) {

    var colorSeleccionado by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            plantilla.colores.forEachIndexed { index, color ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color)
                        .clickable { colorSeleccionado = index }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            plantilla.dibujo.chunked(plantilla.tam).forEach { fila ->
                Row {
                    fila.forEach { valor ->
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(
                                    if (valor == colorSeleccionado) plantilla.colores[valor]
                                    else Color.LightGray
                                )
                                .padding(1.dp)
                        )
                    }
                }
            }
        }
    }
}