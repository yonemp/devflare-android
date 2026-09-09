package site.devflare.app.data.model

enum class SessionSource {
    REMOTE,
    LOCAL_DEMO,
}

data class Session(
    val name: String,
    val email: String,
    val source: SessionSource,
    val userId: String? = null,
)

enum class ProjectStage { Discovery, Build, Review, Live }

data class StudioProject(
    val id: String,
    val name: String,
    val client: String,
    val clientCode: String,
    val owner: String,
    val stage: ProjectStage,
    val progress: Int,
    val updated: String,
)

enum class TaskPriority { Urgent, High, Medium, Low }

enum class TaskColumn { Todo, InProgress, Review, Done }

data class StudioTask(
    val id: String,
    val title: String,
    val project: String,
    val owner: String,
    val ownerInitials: String,
    val due: String,
    val priority: TaskPriority,
    val column: TaskColumn,
    val done: Int,
    val total: Int,
)

enum class InboxKind { Client, Agent, Deal }

data class InboxThread(
    val id: String,
    val sender: String,
    val initials: String,
    val time: String,
    val title: String,
    val preview: String,
    val kind: InboxKind,
    val unread: Boolean,
)

data class Person(
    val id: String,
    val name: String,
    val email: String,
    val company: String,
    val companyCode: String,
    val role: String,
    val status: String,
    val location: String,
    val lastActive: String,
)

data class Note(
    val id: String,
    val title: String,
    val body: String,
    val tags: List<String>,
    val author: String,
    val date: String,
    val pinned: Boolean,
)

data class Agent(
    val id: String,
    val name: String,
    val description: String,
    val status: String,
    val model: String,
    val runs: Int,
    val lastRun: String,
)

data class Automation(
    val id: String,
    val name: String,
    val trigger: String,
    val action: String,
    val owner: String,
    val lastFired: String,
    val enabled: Boolean,
    val failed: Boolean = false,
)

data class Meeting(
    val id: String,
    val title: String,
    val with: String,
    val day: String,
    val time: String,
    val duration: String,
    val where: String,
    val type: String,
)

data class ActivityItem(
    val id: String,
    val actor: String,
    val action: String,
    val time: String,
)

data class ReportMetric(
    val label: String,
    val value: String,
    val delta: String,
    val up: Boolean,
)

data class PipelineSlice(
    val stage: String,
    val value: Int,
)
