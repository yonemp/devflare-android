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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
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
import site.devflare.app.ui.theme.Hairline
import site.devflare.app.ui.theme.TextPrimary
import site.devflare.app.ui.theme.TextSecondary
import site.devflare.app.ui.theme.White

@Composable
fun AutomationsScreen(onBack: () -> Unit) {
    val enabled = remember {
        mutableStateMapOf<String, Boolean>().apply {
            SampleCatalog.automations.forEach { put(it.id, it.enabled) }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Black)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp),
    ) {
        ScreenHeader(
            eyebrow = "Triggers",
            title = "Automations",
            subtitle = "Keep inbox, notes, and reports in sync. Toggles stay on this device for v1.",
            trailing = { BackAction(onBack) },
        )
        Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            SampleCatalog.automations.forEach { item ->
                HairlineCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(item.name, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 15.sp, modifier = Modifier.weight(1f))
                        Switch(
                            checked = enabled[item.id] == true,
                            onCheckedChange = { enabled[item.id] = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Black,
                                checkedTrackColor = White,
                                uncheckedThumbColor = TextSecondary,
                                uncheckedTrackColor = Hairline,
                                uncheckedBorderColor = Hairline,
                            ),
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(item.trigger, color = TextSecondary, fontSize = 13.sp)
                    Spacer(Modifier.height(2.dp))
                    Text(item.action, color = TextSecondary, fontSize = 13.sp)
                    Spacer(Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        if (item.failed) StatusChip("Failed last run", ChipTone.Danger)
                        MetaRow(listOf(item.owner, item.lastFired))
                    }
                }
            }
        }
    }
}
