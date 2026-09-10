package site.devflare.app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import site.devflare.app.data.WorkspaceCatalog
import site.devflare.app.data.model.Session
import site.devflare.app.data.model.SessionSource
import site.devflare.app.ui.components.EmptyState
import site.devflare.app.ui.components.HairlineCard
import site.devflare.app.ui.components.InitialsAvatar
import site.devflare.app.ui.components.MetaRow
import site.devflare.app.ui.components.QuietProgress
import site.devflare.app.ui.components.ScreenHeader
import site.devflare.app.ui.components.SectionLabel
import site.devflare.app.ui.components.StatusChip
import site.devflare.app.ui.theme.Black
import site.devflare.app.ui.theme.TextPrimary
import site.devflare.app.ui.theme.TextSecondary
import site.devflare.app.ui.theme.TextTertiary
import java.util.Calendar

@Composable
fun HomeScreen(
    session: Session?,
    onOpenInbox: () -> Unit,
    onOpenProjects: () -> Unit,
    onOpenMeetings: () -> Unit,
) {
    val name = session?.name?.substringBefore(" ") ?: "DevFlare"
    val greeting = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
        in 5..11 -> "Good morning"
        in 12..17 -> "Good afternoon"
        else -> "Good evening"
    }
    val unread = WorkspaceCatalog.inbox.count { it.unread }
    val openTasks = WorkspaceCatalog.tasks.count { it.column != site.devflare.app.data.model.TaskColumn.Done }
    val live = WorkspaceCatalog.projects.count { it.stage != site.devflare.app.data.model.ProjectStage.Live }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 20.dp),
    ) {
        ScreenHeader(
            eyebrow = if (session?.source == SessionSource.REMOTE) "Live workspace" else "Studio pulse",
            title = "$greeting, $name",
            subtitle = "Inbox, projects, and the next meetings on the board.",
            trailing = {
                InitialsAvatar(initialsFrom(session?.name ?: "DA"), size = 36.dp)
            },
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            MetricTile("$unread", "Unread threads", Modifier.width(148.dp), onOpenInbox)
            MetricTile("$openTasks", "Open tasks", Modifier.width(148.dp), null)
            MetricTile("$live", "Active builds", Modifier.width(148.dp), onOpenProjects)
        }

        SectionLabel("Active projects")
        Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            val projects = WorkspaceCatalog.projects.take(4)
            if (projects.isEmpty()) {
                EmptyState(
                    title = "No projects yet",
                    body = "Active studio work will show up here once a project is in flight.",
                )
            } else {
                projects.forEach { project ->
                    HairlineCard(onClick = onOpenProjects) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(project.name, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 15.sp, modifier = Modifier.weight(1f))
                            StatusChip(project.stage.name)
                        }
                        Spacer(Modifier.height(6.dp))
                        MetaRow(listOf(project.client, project.owner, project.updated))
                        Spacer(Modifier.height(12.dp))
                        QuietProgress(project.progress)
                    }
                }
            }
        }

        SectionLabel("This week")
        Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            val meetings = WorkspaceCatalog.meetings.take(3)
            if (meetings.isEmpty()) {
                EmptyState(
                    title = "Nothing on the calendar",
                    body = "Upcoming reviews and standups will appear here.",
                )
            } else {
                meetings.forEach { meeting ->
                    HairlineCard(onClick = onOpenMeetings) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("${meeting.day}  ${meeting.time}", color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                            Spacer(Modifier.weight(1f))
                            Text(meeting.duration, color = TextTertiary, fontSize = 12.sp)
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(meeting.title, color = TextPrimary, fontSize = 15.sp)
                        Spacer(Modifier.height(4.dp))
                        MetaRow(listOf(meeting.with, meeting.where, meeting.type))
                    }
                }
            }
        }

        SectionLabel("Activity")
        Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            val activity = WorkspaceCatalog.activity
            if (activity.isEmpty()) {
                EmptyState(
                    title = "No activity yet",
                    body = "Comments, stage changes, and agent runs will land in this feed.",
                )
            } else {
                HairlineCard {
                    activity.forEachIndexed { index, item ->
                        if (index > 0) Spacer(Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text(item.actor, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                Text(item.action, color = TextSecondary, fontSize = 13.sp)
                            }
                            Text(item.time, color = TextTertiary, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricTile(value: String, label: String, modifier: Modifier, onClick: (() -> Unit)?) {
    HairlineCard(modifier = modifier, onClick = onClick) {
        Text(value, color = TextPrimary, fontSize = 26.sp, fontWeight = FontWeight.SemiBold, letterSpacing = (-0.6).sp)
        Spacer(Modifier.height(4.dp))
        Text(label, color = TextSecondary, fontSize = 12.sp)
    }
}

private fun initialsFrom(name: String): String {
    val parts = name.trim().split(" ").filter { it.isNotBlank() }
    return when {
        parts.size >= 2 -> "${parts[0].first()}${parts[1].first()}".uppercase()
        parts.size == 1 && parts[0].length >= 2 -> parts[0].take(2).uppercase()
        else -> "DF"
    }
}
