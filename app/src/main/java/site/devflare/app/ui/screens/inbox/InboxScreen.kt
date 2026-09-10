package site.devflare.app.ui.screens.inbox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import site.devflare.app.data.WorkspaceCatalog
import site.devflare.app.data.model.InboxKind
import site.devflare.app.ui.components.ChipTone
import site.devflare.app.ui.components.EmptyState
import site.devflare.app.ui.components.FilterRow
import site.devflare.app.ui.components.HairlineCard
import site.devflare.app.ui.components.InitialsAvatar
import site.devflare.app.ui.components.MetaRow
import site.devflare.app.ui.components.ScreenHeader
import site.devflare.app.ui.components.StatusChip
import site.devflare.app.ui.theme.Black
import site.devflare.app.ui.theme.TextPrimary
import site.devflare.app.ui.theme.TextSecondary
import site.devflare.app.ui.theme.White

@Composable
fun InboxScreen() {
    var filter by rememberSaveable { mutableStateOf("All") }
    val threads = WorkspaceCatalog.inbox.filter { thread ->
        when (filter) {
            "Unread" -> thread.unread
            "Client" -> thread.kind == InboxKind.Client
            "Agent" -> thread.kind == InboxKind.Agent
            "Deal" -> thread.kind == InboxKind.Deal
            else -> true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .statusBarsPadding(),
    ) {
        ScreenHeader(
            eyebrow = "Queue",
            title = "Inbox",
            subtitle = "Client threads, agent notices, and deal follow-ups in one place.",
        )
        FilterRow(listOf("All", "Unread", "Client", "Agent", "Deal"), filter, onSelect = { filter = it })
        Spacer(Modifier.height(12.dp))
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            if (threads.isEmpty()) {
                EmptyState(
                    title = if (WorkspaceCatalog.inbox.isEmpty()) "Inbox is empty" else "Nothing in this queue",
                    body = if (WorkspaceCatalog.inbox.isEmpty()) {
                        "Client threads, agent notices, and deal follow-ups will land here."
                    } else {
                        "No threads match $filter. Clear the filter or wait for the next client ping."
                    },
                )
            } else {
                threads.forEach { thread ->
                    HairlineCard {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box {
                                InitialsAvatar(thread.initials)
                                if (thread.unread) {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(White),
                                    )
                                }
                            }
                            Spacer(Modifier.size(12.dp))
                            Column(Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(thread.sender, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 14.sp, modifier = Modifier.weight(1f))
                                    Text(thread.time, color = TextSecondary, fontSize = 12.sp)
                                }
                                Text(thread.title, color = TextPrimary, fontSize = 14.sp)
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                        Text(thread.preview, color = TextSecondary, fontSize = 13.sp, lineHeight = 18.sp)
                        Spacer(Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            StatusChip(thread.kind.name, if (thread.unread) ChipTone.Live else ChipTone.Neutral)
                            MetaRow(listOf(if (thread.unread) "Waiting" else "Read"))
                        }
                    }
                }
            }
        }
    }
}
