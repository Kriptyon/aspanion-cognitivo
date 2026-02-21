package es.aspanion.cognitivo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import es.aspanion.cognitivo.navigation.ControladorNavegacion
import es.aspanion.cognitivo.ui.theme.AspanionTheme

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