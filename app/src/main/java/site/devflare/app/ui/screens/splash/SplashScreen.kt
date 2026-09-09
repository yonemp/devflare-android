package site.devflare.app.ui.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import site.devflare.app.ui.components.FlareHaloMark
import site.devflare.app.ui.theme.Black
import site.devflare.app.ui.theme.TextPrimary
import site.devflare.app.ui.theme.TextTertiary

@Composable
fun SplashScreen(
    ready: Boolean,
    hasSession: Boolean,
    onFinished: (signedIn: Boolean) -> Unit,
) {
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.92f) }

    LaunchedEffect(Unit) {
        alpha.animateTo(1f, tween(700, easing = EaseOutCubic))
        scale.animateTo(1f, tween(800, easing = EaseOutCubic))
    }

    LaunchedEffect(ready) {
        if (!ready) return@LaunchedEffect
        delay(1100)
        onFinished(hasSession)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .alpha(alpha.value)
                .scale(scale.value),
        ) {
            FlareHaloMark(size = 96.dp)
            Spacer(Modifier.height(22.dp))
            Text(
                text = "DevFlare",
                color = TextPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.8).sp,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "// STUDIO WORKSPACE",
                color = TextTertiary,
                fontSize = 11.sp,
                letterSpacing = 2.sp,
                fontFamily = FontFamily.Monospace,
            )
        }
    }
}
