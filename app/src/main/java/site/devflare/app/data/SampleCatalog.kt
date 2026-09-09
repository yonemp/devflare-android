package site.devflare.app.data

import site.devflare.app.data.model.ActivityItem
import site.devflare.app.data.model.Agent
import site.devflare.app.data.model.Automation
import site.devflare.app.data.model.InboxKind
import site.devflare.app.data.model.InboxThread
import site.devflare.app.data.model.Meeting
import site.devflare.app.data.model.Note
import site.devflare.app.data.model.Person
import site.devflare.app.data.model.PipelineSlice
import site.devflare.app.data.model.ProjectStage
import site.devflare.app.data.model.ReportMetric
import site.devflare.app.data.model.StudioProject
import site.devflare.app.data.model.StudioTask
import site.devflare.app.data.model.TaskColumn
import site.devflare.app.data.model.TaskPriority

object SampleCatalog {
    val projects = listOf(
        StudioProject("p1", "Patient portal v2", "Lumen Health", "LH", "Emma Clark", ProjectStage.Build, 62, "Sep 6, 2026"),
        StudioProject("p2", "Live tracking map", "Atlas Freight", "AF", "Ryan Scott", ProjectStage.Review, 81, "Sep 5, 2026"),
        StudioProject("p3", "Warehouse module", "Harbor Ops", "HO", "Priya Nair", ProjectStage.Discovery, 24, "Sep 4, 2026"),
        StudioProject("p4", "Q3 renewal", "Northwind", "NW", "Marcus Hale", ProjectStage.Live, 100, "Sep 1, 2026"),
        StudioProject("p5", "Brand site refresh", "Kindred", "KD", "Sofia Alvarez", ProjectStage.Build, 47, "Aug 30, 2026"),
        StudioProject("p6", "Ledger exports", "Veil Pay", "VP", "Amina Okoye", ProjectStage.Discovery, 18, "Aug 28, 2026"),
        StudioProject("p7", "Edit bay scheduler", "Orbit Media", "OM", "Chris Lang", ProjectStage.Review, 73, "Aug 22, 2026"),
    )

    val tasks = listOf(
        StudioTask("t1", "Warehouse module IA", "Harbor Ops", "Sofia Alvarez", "SA", "Sep 12", TaskPriority.High, TaskColumn.Todo, 0, 3),
        StudioTask("t2", "Write weekly client digest", "Writer", "Writer", "WR", "Sep 8", TaskPriority.Medium, TaskColumn.Todo, 0, 1),
        StudioTask("t3", "Ledger export schema", "Veil Pay", "Priya Nair", "PN", "Sep 15", TaskPriority.High, TaskColumn.Todo, 0, 2),
        StudioTask("t4", "Ship patient intake v2", "Lumen Portal", "Jonah Reeves", "JR", "Sep 9", TaskPriority.Urgent, TaskColumn.InProgress, 3, 5),
        StudioTask("t5", "Map tile cache for tracking", "Atlas Freight", "Emma Clark", "EC", "Sep 11", TaskPriority.High, TaskColumn.InProgress, 1, 4),
        StudioTask("t6", "CSRF on password reset", "Security", "Security", "SE", "Sep 9", TaskPriority.Urgent, TaskColumn.Review, 2, 2),
        StudioTask("t7", "Motion pass on Kindred hero", "Kindred Site", "Sofia Alvarez", "SA", "Sep 18", TaskPriority.Low, TaskColumn.Review, 1, 2),
        StudioTask("t8", "Onboard Northwind staging", "Northwind", "Ryan Scott", "RS", "Sep 6", TaskPriority.Medium, TaskColumn.Done, 4, 4),
    )

    val inbox = listOf(
        InboxThread(
            "i1", "Emma Clark", "EC", "12m",
            "Patient portal — staging walkthrough",
            "Can we move Friday’s review to 2:30? The clinical team wants one more pass on intake.",
            InboxKind.Client, unread = true,
        ),
        InboxThread(
            "i2", "Planner", "PL", "41m",
            "Auth module plan is ready",
            "4 tasks completed. Security flagged a missing CSRF check on the reset route.",
            InboxKind.Agent, unread = true,
        ),
        InboxThread(
            "i3", "Ryan Scott", "RS", "2h",
            "Tracking map latency",
            "P95 jumped to 1.8s after the region failover. Sharing a HAR from last night.",
            InboxKind.Client, unread = true,
        ),
        InboxThread(
            "i4", "Priya Nair", "PN", "5h",
            "Q3 renewal scope",
            "We’re in for the expansion tier if the warehouse module ships before Oct 12.",
            InboxKind.Deal, unread = false,
        ),
        InboxThread(
            "i5", "Sofia Alvarez", "SA", "Yesterday",
            "Brand kit + motion notes",
            "Attached the updated wordmark. Please keep the lime accent off marketing pages.",
            InboxKind.Client, unread = false,
        ),
        InboxThread(
            "i6", "Amina Okoye", "AO", "Yesterday",
            "Ledger export format",
            "Finance needs CSV + JSON. Can Agents draft the schema before Thursday?",
            InboxKind.Deal, unread = false,
        ),
    )

    val people = listOf(
        Person("pe1", "Emma Clark", "emma@lumen.health", "Lumen Health", "LH", "VP Product", "Active", "Austin", "2m ago"),
        Person("pe2", "Ryan Scott", "ryan@atlasfreight.io", "Atlas Freight", "AF", "CTO", "Active", "Chicago", "18m ago"),
        Person("pe3", "Priya Nair", "priya@harborops.com", "Harbor Ops", "HO", "Head of Ops", "Active", "Seattle", "3h ago"),
        Person("pe4", "Marcus Hale", "marcus@northwind.dev", "Northwind", "NW", "Founder", "Away", "NYC", "Yesterday"),
        Person("pe5", "Sofia Alvarez", "sofia@kindred.studio", "Kindred", "KD", "Design lead", "Active", "Remote", "5h ago"),
        Person("pe6", "Jonah Reeves", "jonah@devflare.site", "DevFlare", "DF", "Studio engineer", "Active", "Toronto", "Just now"),
        Person("pe7", "Amina Okoye", "amina@veilpay.com", "Veil Pay", "VP", "Finance lead", "Invited", "London", "—"),
    )

    val notes = listOf(
        Note(
            "n1", "Lumen clinical review — Sep 4",
            "Nurses want the intake form to remember the last facility. Don’t surface PHI in the agent transcript.",
            listOf("Lumen", "HIPAA"), "Emma Clark", "Sep 5, 2026", pinned = true,
        ),
        Note(
            "n2", "Atlas failover runbook",
            "If us-east tiles 5xx, flip the edge rewrite to the Chicago origin and page Ryan.",
            listOf("Atlas", "Infra"), "Jonah Reeves", "Sep 3, 2026", pinned = true,
        ),
        Note(
            "n3", "Harbor expansion commercial notes",
            "Expansion Tier 2 is \$84k if we include the scanner integration. Legal wants a 30-day out.",
            listOf("Harbor", "Deal"), "Priya Nair", "Sep 2, 2026", pinned = false,
        ),
        Note(
            "n4", "Agent prompt — weekly digest",
            "Summarize closed tasks, blocked clients, and credits used. No system-prompt leakage.",
            listOf("Ops", "Writer"), "Writer", "Aug 28, 2026", pinned = false,
        ),
        Note(
            "n5", "Kindred brand restraint",
            "Public site stays quiet. Product chrome can go darker. Lime is studio-only, never marketing.",
            listOf("Brand"), "Sofia Alvarez", "Aug 26, 2026", pinned = false,
        ),
        Note(
            "n6", "Veil Pay ledger fields",
            "posted_at, currency, amount_minor, counterparty, rail, memo. CSV first, JSON second.",
            listOf("Veil", "Spec"), "Amina Okoye", "Aug 22, 2026", pinned = false,
        ),
    )

    val agents = listOf(
        Agent("a1", "Planner", "Breaks work into tasks and assigns owners", "Ready", "qwen3.5:cloud", 128, "41m ago"),
        Agent("a2", "Researcher", "Pulls project context and conversation history", "Ready", "qwen3.5:cloud", 96, "2h ago"),
        Agent("a3", "Code Engineer", "Writes and explains production code", "Running", "qwen3.5:cloud", 214, "Now"),
        Agent("a4", "Debugger", "Reads logs and returns root cause + fix", "Ready", "qwen3.5:cloud", 71, "Yesterday"),
        Agent("a5", "Writer", "Docs, summaries, and client-facing copy", "Ready", "qwen3.5:cloud", 88, "3h ago"),
        Agent("a6", "QA", "Checks output against the original request", "Ready", "qwen3.5:cloud", 64, "5h ago"),
        Agent("a7", "Security", "Reviews secrets, auth, and injection risk", "Paused", "qwen3.5:cloud", 52, "Sep 4"),
        Agent("a8", "DevOps", "Docker, env, CI, and launch steps", "Ready", "qwen3.5:cloud", 39, "Sep 3"),
    )

    val automations = listOf(
        Automation("au1", "New client thread → Inbox + Slack", "Email from a known domain", "Open Inbox thread and notify #studio", "Jonah Reeves", "12m ago", enabled = true),
        Automation("au2", "Meeting recap → Notes", "Calendar event ends", "Ask Writer for a recap and pin it", "Emma Clark", "Yesterday", enabled = true),
        Automation("au3", "Failed agent run → Debugger", "Agent run status = FAILED", "Open a Debugger task and page on-call", "Security", "Sep 4", enabled = true, failed = true),
        Automation("au4", "Deal stage → Forecast", "Project stage changes", "Refresh Reports pipeline", "Priya Nair", "Sep 2", enabled = true),
        Automation("au5", "Credits > 90% → Digest", "Credits used crosses 90%", "Email the workspace owner", "DevOps", "Never", enabled = false),
    )

    val meetings = listOf(
        Meeting("m1", "Lumen staging walkthrough", "Emma Clark", "Mon", "14:30", "45m", "Meet · Studio 2", "Client"),
        Meeting("m2", "Harbor expansion", "Priya Nair", "Tue", "10:00", "30m", "Phone", "Client"),
        Meeting("m3", "Studio standup", "Studio", "Wed", "09:15", "20m", "Office", "Internal"),
        Meeting("m4", "Atlas latency review", "Ryan Scott", "Wed", "16:00", "40m", "Meet · War room", "Client"),
        Meeting("m5", "Kindred motion pass", "Sofia Alvarez", "Thu", "11:30", "60m", "Figma", "Internal"),
        Meeting("m6", "Veil Pay schema workshop", "Amina Okoye", "Fri", "13:00", "50m", "Meet · Finance", "Client"),
    )

    val activity = listOf(
        ActivityItem("ac1", "Planner", "finished an auth-module plan", "41m ago"),
        ActivityItem("ac2", "Emma Clark", "commented on Patient portal v2", "1h ago"),
        ActivityItem("ac3", "Ryan Scott", "moved Live tracking map to Review", "3h ago"),
        ActivityItem("ac4", "Writer", "drafted the Harbor expansion note", "5h ago"),
        ActivityItem("ac5", "Security", "flagged CSRF on password reset", "Yesterday"),
    )

    val reportMetrics = listOf(
        ReportMetric("Pipeline", "\$479k", "+12% vs last month", up = true),
        ReportMetric("Active projects", "7", "+1 vs last month", up = true),
        ReportMetric("Tasks closed", "38", "+6 vs last month", up = true),
        ReportMetric("Agent runs", "214", "−8% vs last month", up = false),
    )

    val pipeline = listOf(
        PipelineSlice("Discovery", 148),
        PipelineSlice("Build", 156),
        PipelineSlice("Review", 127),
        PipelineSlice("Live", 48),
    )

    val creditsUsed = 810
    val creditsTotal = 3100
}
