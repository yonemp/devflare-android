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
import site.devflare.app.data.model.ProjectStage
import site.devflare.app.ui.components.EmptyState
import site.devflare.app.ui.components.FilterRow
import site.devflare.app.ui.components.HairlineCard
import site.devflare.app.ui.components.MetaRow
import site.devflare.app.ui.components.QuietProgress
import site.devflare.app.ui.components.ScreenHeader
import site.devflare.app.ui.components.StatusChip
import site.devflare.app.ui.theme.Black
import site.devflare.app.ui.theme.TextPrimary

@Composable
fun ProjectsScreen(onBack: () -> Unit) {
    var filter by rememberSaveable { mutableStateOf("All") }
    val projects = SampleCatalog.projects.filter {
        filter == "All" || it.stage.name == filter
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Black)
            .statusBarsPadding(),
    ) {
        ScreenHeader(
            eyebrow = "Pipeline",
            title = "Projects",
            subtitle = "Studio board with the same clients as the desktop workspace.",
            trailing = { BackAction(onBack) },
        )
        FilterRow(listOf("All") + ProjectStage.entries.map { it.name }, filter) { filter = it }
        Spacer(Modifier.height(12.dp))
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            if (projects.isEmpty()) {
                EmptyState("No projects in $filter", "Nothing is sitting in this stage right now.")
            } else {
                projects.forEach { project ->
                    HairlineCard {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(project.name, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 16.sp, modifier = Modifier.weight(1f))
                            StatusChip(project.stage.name)
                        }
                        Spacer(Modifier.height(6.dp))
                        MetaRow(listOf(project.clientCode, project.client, project.owner, project.updated))
                        Spacer(Modifier.height(12.dp))
                        QuietProgress(project.progress)
                    }
                }
            }
        }
    }
}
