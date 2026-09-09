package site.devflare.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import site.devflare.app.ui.theme.HairlineSoft
import site.devflare.app.ui.theme.Surface
import site.devflare.app.ui.theme.White

@Composable
fun FlareMark(
    modifier: Modifier = Modifier,
    color: Color = White,
    strokeWidth: Float = 0.11f,
) {
    Canvas(modifier) {
        val w = size.width
        val h = size.height
        val stroke = w * strokeWidth
        drawLine(
            color = color,
            start = Offset(w * 0.30f, h * 0.78f),
            end = Offset(w * 0.60f, h * 0.24f),
            strokeWidth = stroke,
            cap = StrokeCap.Butt,
        )
        val cx = w * 0.66f
        val cy = h * 0.46f
        val hx = w * 0.30f
        val hy = h * 0.26f
        val waist = w * 0.032f
        val spark = Path().apply {
            moveTo(cx, cy - hy)
            lineTo(cx + waist, cy - waist)
            lineTo(cx + hx, cy)
            lineTo(cx + waist, cy + waist)
            lineTo(cx, cy + hy)
            lineTo(cx - waist, cy + waist)
            lineTo(cx - hx, cy)
            lineTo(cx - waist, cy - waist)
            close()
        }
        drawPath(spark, color)
    }
}

@Composable
fun FlareMarkBadge(size: Dp = 44.dp, corner: Dp = 12.dp) {
    Box(
        modifier = Modifier
            .size(size)
            .background(Surface, RoundedCornerShape(corner)),
    ) {
        FlareMark(modifier = Modifier.size(size))
    }
}

@Composable
fun FlareHaloMark(size: Dp = 88.dp) {
    Box(
        modifier = Modifier
            .size(size)
            .background(Color(0xFF121214), RoundedCornerShape(size)),
    ) {
        Canvas(Modifier.size(size)) {
            drawCircle(
                color = HairlineSoft,
                style = Stroke(width = 1.2f),
            )
        }
        FlareMark(modifier = Modifier.size(size))
    }
}
