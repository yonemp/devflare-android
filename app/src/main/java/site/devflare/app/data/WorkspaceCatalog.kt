package site.devflare.app.data

import site.devflare.app.data.model.ActivityItem
import site.devflare.app.data.model.Agent
import site.devflare.app.data.model.Automation
import site.devflare.app.data.model.InboxThread
import site.devflare.app.data.model.Meeting
import site.devflare.app.data.model.Note
import site.devflare.app.data.model.Person
import site.devflare.app.data.model.PipelineSlice
import site.devflare.app.data.model.ReportMetric
import site.devflare.app.data.model.StudioProject
import site.devflare.app.data.model.StudioTask

/**
 * In-memory workspace. Empty until a live API is wired; screens bind to these
 * collections so the shell stays ready for real data.
 */
object WorkspaceCatalog {
    val projects: List<StudioProject> = emptyList()
    val tasks: List<StudioTask> = emptyList()
    val inbox: List<InboxThread> = emptyList()
    val people: List<Person> = emptyList()
    val notes: List<Note> = emptyList()
    val agents: List<Agent> = emptyList()
    val automations: List<Automation> = emptyList()
    val meetings: List<Meeting> = emptyList()
    val activity: List<ActivityItem> = emptyList()
    val reportMetrics: List<ReportMetric> = emptyList()
    val pipeline: List<PipelineSlice> = emptyList()
    val creditsUsed: Int = 0
    val creditsTotal: Int = 0
}
