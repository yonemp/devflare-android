package site.devflare.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import site.devflare.app.ui.theme.Hairline
import site.devflare.app.ui.theme.HairlineSoft
import site.devflare.app.ui.theme.NearBlack
import site.devflare.app.ui.theme.Surface
import site.devflare.app.ui.theme.SurfaceRaised
import site.devflare.app.ui.theme.TextPrimary
import site.devflare.app.ui.theme.TextSecondary
import site.devflare.app.ui.theme.TextTertiary
import site.devflare.app.ui.theme.White

val CardShape = RoundedCornerShape(14.dp)
val ChipShape = RoundedCornerShape(999.dp)

@Composable
fun ScreenHeader(
    eyebrow: String,
    title: String,
    subtitle: String,
    trailing: (@Composable RowScope.() -> Unit)? = null,
) {
    Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = eyebrow.uppercase(),
                    color = TextTertiary,
                    fontSize = 10.sp,
                    letterSpacing = 1.6.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = title,
                    color = TextPrimary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = (-0.8).sp,
                )
            }
            trailing?.invoke(this)
        }
        Spacer(Modifier.height(6.dp))
        Text(text = subtitle, color = TextSecondary, fontSize = 14.sp, lineHeight = 20.sp)
    }
}

@Composable
fun HairlineCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val base = Modifier
        .fillMaxWidth()
        .then(modifier)
        .clip(CardShape)
        .background(Surface)
        .border(1.dp, HairlineSoft, CardShape)
        .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
        .padding(16.dp)
    Column(base, content = content)
}

@Composable
fun InitialsAvatar(initials: String, size: Dp = 36.dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(SurfaceRaised)
            .border(1.dp, Hairline, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = initials,
            color = TextPrimary,
            fontSize = (size.value * 0.34f).sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = (-0.3).sp,
        )
    }
}

@Composable
fun StatusChip(text: String, tone: ChipTone = ChipTone.Neutral) {
    val (fg, bg, border) = when (tone) {
        ChipTone.Neutral -> Triple(TextSecondary, SurfaceRaised, Hairline)
        ChipTone.Live -> Triple(TextPrimary, Color(0xFF1A1A1C), Hairline)
        ChipTone.Warn -> Triple(Color(0xFFFFD60A), Color(0x221F1A00), Color(0x33FFD60A))
        ChipTone.Danger -> Triple(Color(0xFFFF453A), Color(0x22FF453A), Color(0x33FF453A))
        ChipTone.Good -> Triple(Color(0xFF32D74B), Color(0x2214341A), Color(0x3332D74B))
    }
    Text(
        text = text,
        color = fg,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .clip(ChipShape)
            .background(bg)
            .border(1.dp, border, ChipShape)
            .padding(horizontal = 8.dp, vertical = 3.dp),
    )
}

enum class ChipTone { Neutral, Live, Warn, Danger, Good }

@Composable
fun FilterRow(
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        options.forEach { option ->
            val active = option == selected
            Text(
                text = option,
                color = if (active) NearBlack else TextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .clip(ChipShape)
                    .background(if (active) White else Surface)
                    .border(1.dp, if (active) White else Hairline, ChipShape)
                    .clickable { onSelect(option) }
                    .padding(horizontal = 12.dp, vertical = 7.dp),
            )
        }
    }
}

@Composable
fun QuietProgress(progress: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        LinearProgressIndicator(
            progress = { (progress / 100f).coerceIn(0f, 1f) },
            modifier = Modifier
                .weight(1f)
                .height(3.dp)
                .clip(ChipShape),
            color = White,
            trackColor = HairlineSoft,
            strokeCap = StrokeCap.Round,
        )
        Spacer(Modifier.width(10.dp))
        Text("$progress%", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun EmptyState(title: String, body: String) {
    HairlineCard {
        Text(title, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 16.sp)
        Spacer(Modifier.height(6.dp))
        Text(body, color = TextSecondary, fontSize = 13.sp, lineHeight = 18.sp)
    }
}

@Composable
fun SectionLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text.uppercase(),
        color = TextTertiary,
        fontSize = 10.sp,
        letterSpacing = 1.5.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
        modifier = modifier.padding(horizontal = 20.dp, vertical = 8.dp),
    )
}

@Composable
fun MetaRow(items: List<String>) {
    Text(
        text = items.filter { it.isNotBlank() }.joinToString("  ·  "),
        color = TextTertiary,
        fontSize = 12.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

fun contentPad(): PaddingValues = PaddingValues(bottom = 28.dp)
