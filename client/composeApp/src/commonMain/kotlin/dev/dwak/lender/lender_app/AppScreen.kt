package dev.dwak.lender.lender_app

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.test.crowdsource
import com.example.test.groups
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuitx.navigation.intercepting.NavigationEventListener
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import dev.dwak.lender.icons.home
import kotlinx.serialization.Serializable

sealed interface AppEvents {
  data class SelectBottomBarTab(val tab: BottomBarTabs) : AppEvents
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

enum class BottomBarTabs(val label: String, val icon: ImageVector) {
  HOME(
    "Home",
    home
  ),
  LENDS(
    "Lends",
    crowdsource
  ),
  GROUPS(
    "Groups",
    groups
  )
}