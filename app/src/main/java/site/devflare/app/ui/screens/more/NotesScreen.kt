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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import site.devflare.app.data.SampleCatalog
import site.devflare.app.ui.components.ChipTone
import site.devflare.app.ui.components.EmptyState
import site.devflare.app.ui.components.FilterRow
import site.devflare.app.ui.components.HairlineCard
import site.devflare.app.ui.components.MetaRow
import site.devflare.app.ui.components.ScreenHeader
import site.devflare.app.ui.components.StatusChip
import site.devflare.app.ui.theme.Black
import site.devflare.app.ui.theme.TextPrimary
import site.devflare.app.ui.theme.TextSecondary

@Composable
fun NotesScreen(onBack: () -> Unit) {
    var filter by rememberSaveable { mutableStateOf("All notes") }
    val notes = SampleCatalog.notes.filter { filter == "All notes" || it.pinned }

    Column(
        Modifier
            .fillMaxSize()
            .background(Black)
            .statusBarsPadding(),
    ) {
        ScreenHeader(
            eyebrow = "Library",
            title = "Notes",
            subtitle = "Meeting recaps, runbooks, and brand constraints the studio actually uses.",
            trailing = { BackAction(onBack) },
        )
        FilterRow(listOf("All notes", "Pinned"), filter) { filter = it }
        Spacer(Modifier.height(12.dp))
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            if (notes.isEmpty()) {
                EmptyState("No pinned notes", "Pin a recap or runbook to keep it at the top of the library.")
            } else {
                notes.forEach { note ->
                    HairlineCard {
                        Row {
                            Text(note.title, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 15.sp, modifier = Modifier.weight(1f))
                            if (note.pinned) StatusChip("Pinned", ChipTone.Live)
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(note.body, color = TextSecondary, fontSize = 13.sp, lineHeight = 19.sp)
                        Spacer(Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            note.tags.forEach { StatusChip(it) }
                        }
                        Spacer(Modifier.height(8.dp))
                        MetaRow(listOf(note.author, note.date))
                    }
                }
            }
        }
    }
}
