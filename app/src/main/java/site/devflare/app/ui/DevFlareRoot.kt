package site.devflare.app.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import site.devflare.app.data.auth.AuthRepository
import site.devflare.app.data.auth.SessionStore
import site.devflare.app.ui.navigation.BottomTabs
import site.devflare.app.ui.navigation.Dest
import site.devflare.app.ui.navigation.TabRoutes
import site.devflare.app.ui.screens.auth.LoginScreen
import site.devflare.app.ui.screens.home.HomeScreen
import site.devflare.app.ui.screens.inbox.InboxScreen
import site.devflare.app.ui.screens.more.AgentsScreen
import site.devflare.app.ui.screens.more.AutomationsScreen
import site.devflare.app.ui.screens.more.MeetingsScreen
import site.devflare.app.ui.screens.more.MoreScreen
import site.devflare.app.ui.screens.more.NotesScreen
import site.devflare.app.ui.screens.more.PeopleScreen
import site.devflare.app.ui.screens.more.ProjectsScreen
import site.devflare.app.ui.screens.more.ReportsScreen
import site.devflare.app.ui.screens.splash.SplashScreen
import site.devflare.app.ui.screens.tasks.TasksScreen
import site.devflare.app.ui.theme.Black
import site.devflare.app.ui.theme.HairlineSoft
import site.devflare.app.ui.theme.TextSecondary
import site.devflare.app.ui.theme.TextTertiary
import site.devflare.app.ui.theme.White

@Composable
fun DevFlareRoot(
    sessionStore: SessionStore,
    authRepository: AuthRepository,
) {
    val navController = rememberNavController()
    val vm: SessionViewModel = viewModel(factory = SessionViewModel.factory(sessionStore, authRepository))
    val session by vm.session.collectAsStateWithLifecycle()
    val ready by vm.ready.collectAsStateWithLifecycle()
    val backStack by navController.currentBackStackEntryAsState()
    val current = backStack?.destination?.route
    val showTabs = current in TabRoutes

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
    ) {
        NavHost(
            navController = navController,
            startDestination = Dest.Splash.route,
            modifier = Modifier.weight(1f),
            enterTransition = {
                fadeIn(tween(220)) + slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(280),
                )
            },
            exitTransition = { fadeOut(tween(180)) },
            popEnterTransition = { fadeIn(tween(180)) },
            popExitTransition = {
                fadeOut(tween(180)) + slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(240),
                )
            },
        ) {
            composable(Dest.Splash.route) {
                SplashScreen(
                    ready = ready,
                    hasSession = session != null,
                    onFinished = { signedIn ->
                        navController.navigate(if (signedIn) Dest.Home.route else Dest.Login.route) {
                            popUpTo(Dest.Splash.route) { inclusive = true }
                        }
                    },
                )
            }
            composable(Dest.Login.route) {
                val login by vm.login.collectAsStateWithLifecycle()
                LoginScreen(
                    state = login,
                    onEmail = vm::updateEmail,
                    onPassword = vm::updatePassword,
                    onSubmit = {
                        vm.signIn {
                            navController.navigate(Dest.Home.route) {
                                popUpTo(Dest.Login.route) { inclusive = true }
                            }
                        }
                    },
                )
            }
            composable(Dest.Home.route) {
                HomeScreen(session = session, onOpenInbox = {
                    navController.navigate(Dest.Inbox.route)
                }, onOpenProjects = {
                    navController.navigate(Dest.Projects.route)
                }, onOpenMeetings = {
                    navController.navigate(Dest.Meetings.route)
                })
            }
            composable(Dest.Inbox.route) { InboxScreen() }
            composable(Dest.Tasks.route) { TasksScreen() }
            composable(Dest.More.route) {
                MoreScreen(
                    session = session,
                    onOpen = { dest -> navController.navigate(dest.route) },
                    onSignOut = {
                        vm.signOut {
                            navController.navigate(Dest.Login.route) {
                                popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                            }
                        }
                    },
                )
            }
            composable(Dest.People.route) { PeopleScreen(onBack = { navController.popBackStack() }) }
            composable(Dest.Notes.route) { NotesScreen(onBack = { navController.popBackStack() }) }
            composable(Dest.Agents.route) { AgentsScreen(onBack = { navController.popBackStack() }) }
            composable(Dest.Reports.route) { ReportsScreen(onBack = { navController.popBackStack() }) }
            composable(Dest.Automations.route) { AutomationsScreen(onBack = { navController.popBackStack() }) }
            composable(Dest.Meetings.route) { MeetingsScreen(onBack = { navController.popBackStack() }) }
            composable(Dest.Projects.route) { ProjectsScreen(onBack = { navController.popBackStack() }) }
        }

        if (showTabs) {
            StudioTabBar(
                currentRoute = current,
                onSelect = { route ->
                    navController.navigate(route) {
                        popUpTo(Dest.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

@Composable
private fun StudioTabBar(
    currentRoute: String?,
    onSelect: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xF20B0B0C))
            .windowInsetsPadding(WindowInsets.navigationBars),
    ) {
        HorizontalDivider(color = HairlineSoft, thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BottomTabs.forEach { tab ->
                val selected = currentRoute == tab.dest.route
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelect(tab.dest.route) }
                        .padding(vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.label,
                        tint = if (selected) White else TextTertiary,
                        modifier = Modifier.size(22.dp),
                    )
                    Text(
                        text = tab.label,
                        color = if (selected) White else TextSecondary,
                        fontSize = 11.sp,
                    )
                }
            }
        }
    }
}
