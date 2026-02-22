package es.aspanion.cognitivo.ui.juegos.pintar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.aspanion.cognitivo.ui.juegos.pintar.data.obtenerPlantillas
import es.aspanion.cognitivo.ui.juegos.pintar.model.PlantillaPintar

@Composable
fun PantallaSeleccionPlantilla(
    nivel: String,
    onPlantillaSeleccionada: (PlantillaPintar) -> Unit
) {
    val plantillas = obtenerPlantillas(nivel)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Elige un dibujo", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(plantillas) { plantilla ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPlantillaSeleccionada(plantilla) }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = plantilla.icono, style = MaterialTheme.typography.displaySmall)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = plantilla.nombre, style = MaterialTheme.typography.titleLarge)
                    }
                }
            }
        }
    }
}
