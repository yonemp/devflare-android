package site.devflare.app.ui.screens.more

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Notes
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import site.devflare.app.data.WorkspaceCatalog
import site.devflare.app.data.model.Session
import site.devflare.app.data.model.SessionSource
import site.devflare.app.ui.components.ChipTone
import site.devflare.app.ui.components.HairlineCard
import site.devflare.app.ui.components.InitialsAvatar
import site.devflare.app.ui.components.MetaRow
import site.devflare.app.ui.components.QuietProgress
import site.devflare.app.ui.components.ScreenHeader
import site.devflare.app.ui.components.SectionLabel
import site.devflare.app.ui.components.StatusChip
import site.devflare.app.ui.navigation.Dest
import site.devflare.app.ui.theme.Black
import site.devflare.app.ui.theme.TextPrimary
import site.devflare.app.ui.theme.TextSecondary
import site.devflare.app.ui.theme.TextTertiary

@Composable
fun MoreScreen(
    session: Session?,
    onOpen: (Dest) -> Unit,
    onSignOut: () -> Unit,
) {
    val used = WorkspaceCatalog.creditsUsed
    val total = WorkspaceCatalog.creditsTotal
    val creditPercent = if (total <= 0) 0 else ((used.toFloat() / total) * 100).toInt()
    val creditMeta = if (total <= 0) listOf("No usage this cycle") else listOf("$used / $total", "this cycle")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp),
    ) {
        ScreenHeader(
            eyebrow = "Workspace",
            title = "More",
            subtitle = "People, notes, agents, and the rest of the studio.",
        )

        Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            HairlineCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    InitialsAvatar(session?.name?.let { initials(it) } ?: "DF", size = 42.dp)
                    Spacer(Modifier.padding(start = 12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(session?.name ?: "Studio", color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 16.sp)
                        Text(session?.email ?: "workspace@devflare.site", color = TextSecondary, fontSize = 13.sp)
                    }
                    StatusChip(
                        if (session?.source == SessionSource.REMOTE) "Auth.js" else "Offline",
                        if (session?.source == SessionSource.REMOTE) ChipTone.Good else ChipTone.Neutral,
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text("Credits used", color = TextTertiary, fontSize = 11.sp)
                Spacer(Modifier.height(8.dp))
                QuietProgress(creditPercent)
                Spacer(Modifier.height(6.dp))
                MetaRow(creditMeta)
            }
        }

        SectionLabel("Studio")
        Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OverflowRow("People", "Clients and teammates", Icons.Outlined.Groups) { onOpen(Dest.People) }
            OverflowRow("Notes", "Recaps, runbooks, constraints", Icons.AutoMirrored.Outlined.Notes) { onOpen(Dest.Notes) }
            OverflowRow("Agents", "Studio agents", Icons.Outlined.SmartToy) { onOpen(Dest.Agents) }
            OverflowRow("Reports", "Pipeline and throughput", Icons.Outlined.BarChart) { onOpen(Dest.Reports) }
            OverflowRow("Automations", "Triggers that keep the queue moving", Icons.Outlined.AutoAwesome) { onOpen(Dest.Automations) }
            OverflowRow("Meetings", "This week’s schedule", Icons.Outlined.CalendarMonth) { onOpen(Dest.Meetings) }
            OverflowRow("Projects", "Studio pipeline", Icons.Outlined.Folder) { onOpen(Dest.Projects) }
        }

        SectionLabel("Session")
        Column(Modifier.padding(horizontal = 20.dp)) {
            OverflowRow("Sign out", "Return to the portal", Icons.AutoMirrored.Outlined.Logout, onClick = onSignOut)
        }
    }
}

@Composable
private fun OverflowRow(title: String, subtitle: String, icon: ImageVector, onClick: () -> Unit) {
    HairlineCard(onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = TextPrimary, modifier = Modifier.padding(end = 12.dp))
            Column(Modifier.weight(1f)) {
                Text(title, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 15.sp)
                Text(subtitle, color = TextSecondary, fontSize = 12.sp)
            }
            Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = TextTertiary)
        }
    }
}

private fun initials(name: String): String {
    val parts = name.trim().split(" ").filter { it.isNotBlank() }
    return if (parts.size >= 2) "${parts[0].first()}${parts[1].first()}".uppercase()
    else name.take(2).uppercase()
}
