package es.aspanion.cognitivo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Configuración para el modo oscuro
private val DarkColorScheme = darkColorScheme(
    primary = AzulCieloSuave,
    secondary = VerdeMenta,
    tertiary = NaranjaPastel,
    background = AzulNocheFondo,
    surface = AzulNocheFondo,
    onPrimary = TextoGrisOscuro,
    onSecondary = TextoGrisOscuro,
    onBackground = GrisSuaveTexto,
    onSurface = GrisSuaveTexto
)

// Configuración para el modo normal (luz)
private val LightColorScheme = lightColorScheme(
    primary = AzulCieloSuave,
    secondary = VerdeMenta,
    tertiary = NaranjaPastel,
    background = FondoBlancoCalido,
    surface = FondoBlancoCalido,
    onPrimary = TextoGrisOscuro,
    onSecondary = TextoGrisOscuro,
    onBackground = TextoGrisOscuro,
    onSurface = TextoGrisOscuro
)

@Composable
fun AspanionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}