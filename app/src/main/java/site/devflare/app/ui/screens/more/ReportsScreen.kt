package site.devflare.app.ui.screens.more

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import site.devflare.app.data.WorkspaceCatalog
import site.devflare.app.ui.components.EmptyState
import site.devflare.app.ui.components.HairlineCard
import site.devflare.app.ui.components.MetaRow
import site.devflare.app.ui.components.ScreenHeader
import site.devflare.app.ui.components.SectionLabel
import site.devflare.app.ui.theme.Black
import site.devflare.app.ui.theme.HairlineSoft
import site.devflare.app.ui.theme.TextPrimary
import site.devflare.app.ui.theme.TextSecondary
import site.devflare.app.ui.theme.White

@Composable
fun ReportsScreen(onBack: () -> Unit) {
    val metrics = WorkspaceCatalog.reportMetrics
    val pipeline = WorkspaceCatalog.pipeline
    val projects = WorkspaceCatalog.projects.take(6)
    val max = pipeline.maxOfOrNull { it.value }?.coerceAtLeast(1) ?: 1
    val empty = metrics.isEmpty() && pipeline.isEmpty() && projects.isEmpty()
    Column(
        Modifier
            .fillMaxSize()
            .background(Black)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp),
    ) {
        ScreenHeader(
            eyebrow = "Pulse",
            title = "Reports",
            subtitle = "Pipeline, throughput, and agent usage once work is in flight.",
            trailing = { BackAction(onBack) },
        )
        if (empty) {
            Column(Modifier.padding(horizontal = 20.dp)) {
                EmptyState(
                    title = "No reports yet",
                    body = "Pipeline value, closed tasks, and agent runs will show here after the first cycle.",
                )
            }
        } else {
            Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                metrics.chunked(2).forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        row.forEach { metric ->
                            HairlineCard(modifier = Modifier.weight(1f)) {
                                Text(metric.label, color = TextSecondary, fontSize = 12.sp)
                                Spacer(Modifier.height(6.dp))
                                Text(metric.value, color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.SemiBold, letterSpacing = (-0.5).sp)
                                Spacer(Modifier.height(4.dp))
                                Text(metric.delta, color = TextSecondary, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
            if (pipeline.isNotEmpty()) {
                SectionLabel("Pipeline by stage")
                HairlineCard(modifier = Modifier.padding(horizontal = 20.dp)) {
                    pipeline.forEachIndexed { index, slice ->
                        if (index > 0) Spacer(Modifier.height(14.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(slice.stage, color = TextPrimary, fontSize = 13.sp, modifier = Modifier.weight(1f))
                            Text("$${slice.value}k", color = TextSecondary, fontSize = 12.sp)
                        }
                        Spacer(Modifier.height(6.dp))
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(99.dp))
                                .background(HairlineSoft),
                        ) {
                            Box(
                                Modifier
                                    .fillMaxWidth(slice.value / max.toFloat())
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(99.dp))
                                    .background(White),
                            )
                        }
                    }
                }
            }
            if (projects.isNotEmpty()) {
                SectionLabel("Value by project")
                Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    projects.forEach { project ->
                        HairlineCard {
                            Text(project.name, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                            Spacer(Modifier.height(4.dp))
                            MetaRow(listOf(project.client, project.stage.name, "${project.progress}%"))
                        }
                    }
                }
            }
        }
    }
}
