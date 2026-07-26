package dev.dwak.lender.lender_app

import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuitx.navigation.intercepting.NavigationEventListener
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import dev.dwak.lender.lender_app.tabs.BottomBarTabs
import kotlinx.serialization.Serializable

sealed interface AppEvents {
  data class SelectBottomBarTab(val tab: BottomBarTabs) : AppEvents
}

sealed interface AppState : CircuitUiState {
  data object Loading : AppState

  data object NeedsServerConfig : AppState

  data class LoggedIn(
    val navigationInterceptors: Set<NavigationInterceptor>,
    val navigationEventInterceptors: Set<NavigationEventListener>,
    val selectedTab: BottomBarTabs = BottomBarTabs.HOME,
    val dispatch: (AppEvents) -> Unit,
  ) : AppState

  data object LoggedOut : AppState
}

@Parcelize
@Serializable
data object AppScreen : Screen
