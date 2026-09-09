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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import site.devflare.app.data.SampleCatalog
import site.devflare.app.ui.components.ChipTone
import site.devflare.app.ui.components.HairlineCard
import site.devflare.app.ui.components.MetaRow
import site.devflare.app.ui.components.ScreenHeader
import site.devflare.app.ui.components.StatusChip
import site.devflare.app.ui.theme.Black
import site.devflare.app.ui.theme.TextPrimary
import site.devflare.app.ui.theme.TextSecondary

@Composable
fun AgentsScreen(onBack: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Black)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp),
    ) {
        ScreenHeader(
            eyebrow = "Subagents",
            title = "Agents",
            subtitle = "The eight DevFlare subagents. Desktop still owns the live AI workspace.",
            trailing = { BackAction(onBack) },
        )
        Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            SampleCatalog.agents.forEach { agent ->
                HairlineCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(agent.name, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 16.sp, modifier = Modifier.weight(1f))
                        StatusChip(
                            agent.status,
                            when (agent.status) {
                                "Running" -> ChipTone.Good
                                "Paused" -> ChipTone.Warn
                                else -> ChipTone.Neutral
                            },
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(agent.description, color = TextSecondary, fontSize = 13.sp)
                    Spacer(Modifier.height(10.dp))
                    MetaRow(listOf(agent.model, "${agent.runs} runs", agent.lastRun))
                }
            }
        }
    }
}
