package dev.dwak.lender.lender_app

import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuitx.navigation.intercepting.NavigationEventListener
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import kotlinx.serialization.Serializable

sealed interface AppEvents {
  data class SelectBottomBarTab(val tab: BottomBarTabs): AppEvents
}

data class AppState(
  val navigationInterceptors: Set<NavigationInterceptor>,
  val navigationEventInterceptors: Set<NavigationEventListener>,
  val isLoggedIn: Boolean = false,
  val selectedTab: BottomBarTabs = BottomBarTabs.HOME,
  val dispatch: (AppEvents) -> Unit,
  override val loading: Boolean,
) : CircuitUiState, Loadable

@Parcelize
@Serializable
data object AppScreen : Screen

enum class BottomBarTabs(val label: String) {
  HOME("Home"), LENDS("Lends"), GROUPS("Groups")
}