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
fun MeetingsScreen(onBack: () -> Unit) {
    val grouped = SampleCatalog.meetings.groupBy { it.day }
    Column(
        Modifier
            .fillMaxSize()
            .background(Black)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp),
    ) {
        ScreenHeader(
            eyebrow = "Schedule",
            title = "Meetings",
            subtitle = "This week’s studio calendar — client reviews, standups, and workshops.",
            trailing = { BackAction(onBack) },
        )
        Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            grouped.forEach { (day, meetings) ->
                meetings.forEach { meeting ->
                    HairlineCard {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(day, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Spacer(Modifier.padding(horizontal = 8.dp))
                            Text(meeting.time, color = TextPrimary, fontSize = 14.sp)
                            Spacer(Modifier.weight(1f))
                            Text(meeting.duration, color = TextSecondary, fontSize = 12.sp)
                        }
                        Spacer(Modifier.height(6.dp))
                        Text(meeting.title, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 16.sp)
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            StatusChip(meeting.type, if (meeting.type == "Client") ChipTone.Live else ChipTone.Neutral)
                            MetaRow(listOf(meeting.with, meeting.where))
                        }
                    }
                }
            }
        }
    }
}
