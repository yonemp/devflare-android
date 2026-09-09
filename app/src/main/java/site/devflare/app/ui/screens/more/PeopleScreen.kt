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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import site.devflare.app.data.SampleCatalog
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

@Composable
fun PeopleScreen(onBack: () -> Unit) {
    var filter by rememberSaveable { mutableStateOf("All") }
    val people = SampleCatalog.people.filter {
        filter == "All" || it.status == filter
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Black)
            .statusBarsPadding(),
    ) {
        ScreenHeader(
            eyebrow = "Directory",
            title = "People",
            subtitle = "Clients, operators, and studio teammates.",
            trailing = { BackAction(onBack) },
        )
        FilterRow(listOf("All", "Active", "Away", "Invited"), filter) { filter = it }
        Spacer(Modifier.height(12.dp))
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            if (people.isEmpty()) {
                EmptyState("No one in this state", "Nobody is marked $filter right now.")
            } else {
                people.forEach { person ->
                    HairlineCard {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            InitialsAvatar(person.name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString(""))
                            Spacer(Modifier.padding(start = 12.dp))
                            Column(Modifier.weight(1f)) {
                                Text(person.name, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 15.sp)
                                Text(person.email, color = TextSecondary, fontSize = 12.sp)
                            }
                            StatusChip(
                                person.status,
                                when (person.status) {
                                    "Active" -> ChipTone.Good
                                    "Away" -> ChipTone.Warn
                                    else -> ChipTone.Neutral
                                },
                            )
                        }
                        Spacer(Modifier.height(10.dp))
                        MetaRow(listOf(person.role, person.company, person.location, person.lastActive))
                    }
                }
            }
        }
    }
}
