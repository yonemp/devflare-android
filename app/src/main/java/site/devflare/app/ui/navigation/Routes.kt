package site.devflare.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Dest(val route: String) {
    data object Splash : Dest("splash")
    data object Login : Dest("login")
    data object Home : Dest("home")
    data object Inbox : Dest("inbox")
    data object Tasks : Dest("tasks")
    data object More : Dest("more")
    data object People : Dest("people")
    data object Notes : Dest("notes")
    data object Agents : Dest("agents")
    data object Reports : Dest("reports")
    data object Automations : Dest("automations")
    data object Meetings : Dest("meetings")
    data object Projects : Dest("projects")
}

data class TabItem(
    val dest: Dest,
    val label: String,
    val icon: ImageVector,
)

val BottomTabs = listOf(
    TabItem(Dest.Home, "Home", Icons.Outlined.Home),
    TabItem(Dest.Inbox, "Inbox", Icons.Outlined.Inbox),
    TabItem(Dest.Tasks, "Tasks", Icons.Outlined.CheckBox),
    TabItem(Dest.More, "More", Icons.Outlined.GridView),
)

val TabRoutes = BottomTabs.map { it.dest.route }.toSet()
