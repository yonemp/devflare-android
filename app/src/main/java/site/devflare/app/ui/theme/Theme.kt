package site.devflare.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

private val DevFlareDarkScheme = darkColorScheme(
    primary = White,
    onPrimary = Black,
    primaryContainer = SurfaceRaised,
    onPrimaryContainer = TextPrimary,
    secondary = TextSecondary,
    onSecondary = Black,
    secondaryContainer = Surface,
    onSecondaryContainer = TextPrimary,
    tertiary = TextPrimary,
    onTertiary = Black,
    background = Black,
    onBackground = TextPrimary,
    surface = NearBlack,
    onSurface = TextPrimary,
    surfaceVariant = Surface,
    onSurfaceVariant = TextSecondary,
    outline = Hairline,
    outlineVariant = HairlineSoft,
    error = Danger,
    onError = White,
    errorContainer = Color(0xFF3A1210),
    onErrorContainer = Color(0xFFFFB4AB),
    inverseSurface = TextPrimary,
    inverseOnSurface = Black,
    inversePrimary = Black,
    scrim = Color(0xCC000000),
)

@Composable
fun DevFlareTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? android.app.Activity)?.window ?: return@SideEffect
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = android.graphics.Color.TRANSPARENT
            window.navigationBarColor = android.graphics.Color.BLACK
            WindowInsetsControllerCompat(window, view).apply {
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = DevFlareDarkScheme,
        typography = DevFlareTypography,
        content = content,
    )
}
