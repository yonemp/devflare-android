package site.devflare.app.ui.screens.tasks

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
import site.devflare.app.data.WorkspaceCatalog
import site.devflare.app.data.model.TaskColumn
import site.devflare.app.data.model.TaskPriority
import site.devflare.app.ui.components.ChipTone
import site.devflare.app.ui.components.EmptyState
import site.devflare.app.ui.components.FilterRow
import site.devflare.app.ui.components.HairlineCard
import site.devflare.app.ui.components.InitialsAvatar
import site.devflare.app.ui.components.MetaRow
import site.devflare.app.ui.components.QuietProgress
import site.devflare.app.ui.components.ScreenHeader
import site.devflare.app.ui.components.SectionLabel
import site.devflare.app.ui.components.StatusChip
import site.devflare.app.ui.theme.Black
import site.devflare.app.ui.theme.TextPrimary

@Composable
fun TasksScreen() {
    var filter by rememberSaveable { mutableStateOf("All") }
    val tasks = WorkspaceCatalog.tasks.filter { task ->
        when (filter) {
            "Urgent" -> task.priority == TaskPriority.Urgent
            "High" -> task.priority == TaskPriority.High
            "Medium" -> task.priority == TaskPriority.Medium
            "Low" -> task.priority == TaskPriority.Low
            else -> true
        }
    }
    val groups = listOf(
        "To do" to TaskColumn.Todo,
        "In progress" to TaskColumn.InProgress,
        "Review" to TaskColumn.Review,
        "Done" to TaskColumn.Done,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .statusBarsPadding(),
    ) {
        ScreenHeader(
            eyebrow = "Board",
            title = "Tasks",
            subtitle = "Priorities, owners, and the next due dates across the studio.",
        )
        FilterRow(listOf("All", "Urgent", "High", "Medium", "Low"), filter) { filter = it }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp),
        ) {
            if (tasks.isEmpty()) {
                Column(Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                    EmptyState(
                        title = if (WorkspaceCatalog.tasks.isEmpty()) "No tasks yet" else "No tasks at this priority",
                        body = if (WorkspaceCatalog.tasks.isEmpty()) {
                            "Work you assign will show up on this board, grouped by stage."
                        } else {
                            "The $filter lane is clear. Switch filters to see the rest of the board."
                        },
                    )
                }
            } else {
                groups.forEach { (label, column) ->
                    val items = tasks.filter { it.column == column }
                    if (items.isEmpty()) return@forEach
                    SectionLabel("$label  ·  ${items.size}")
                    Column(
                        Modifier.padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        items.forEach { task ->
                            HairlineCard {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(task.title, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 15.sp, modifier = Modifier.weight(1f))
                                    StatusChip(
                                        task.priority.name,
                                        when (task.priority) {
                                            TaskPriority.Urgent -> ChipTone.Danger
                                            TaskPriority.High -> ChipTone.Warn
                                            TaskPriority.Low -> ChipTone.Neutral
                                            TaskPriority.Medium -> ChipTone.Live
                                        },
                                    )
                                }
                                Spacer(Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    InitialsAvatar(task.ownerInitials, size = 24.dp)
                                    Spacer(Modifier.padding(start = 8.dp))
                                    MetaRow(listOf(task.owner, task.project, "Due ${task.due}"))
                                }
                                Spacer(Modifier.height(12.dp))
                                QuietProgress(((task.done.toFloat() / task.total.coerceAtLeast(1)) * 100).toInt())
                            }
                        }
                    }
                }
            }
        }
    }
}
