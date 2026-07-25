package dev.dwak.lender.lender_app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.mohamedrejeb.calf.ui.ExperimentalCalfUiApi
import com.mohamedrejeb.calf.ui.navigation.AdaptiveNavigationBar
import com.mohamedrejeb.calf.ui.navigation.AdaptiveScaffold
import com.mohamedrejeb.calf.ui.navigation.UIKitUITabBarItem
import com.mohamedrejeb.calf.ui.uikit.UIKitImage
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.navstack.rememberSaveableNavStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.runtime.Navigator
import com.slack.circuitx.gesturenavigation.GestureNavigationDecorationFactory
import com.slack.circuitx.navigation.intercepting.rememberInterceptingNavigator
import dev.dwak.lender.feature.auth.navigation.api.AuthScreens
import dev.dwak.lender.feature.groups.navigation.GroupsScreens
import dev.dwak.lender.feature.home.navigation.HomeScreens
import dev.dwak.lender.feature.lend.navigation.LendScreens
import dev.dwak.lender.feature.profile.navigation.ProfileScreens
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

@OptIn(ExperimentalCalfUiApi::class)
@CircuitInject(
  screen = AppScreen::class,
  scope = AppScope::class
)
@Inject
@Composable
fun AppUi(
  state: AppState,
  modifier: Modifier = Modifier
) {
  val navStack =
    rememberSaveableNavStack(root = if (state.isLoggedIn && !state.loading) HomeScreens.Home else AuthScreens.Launch)

  val interceptedNavigator = rememberInterceptingNavigator(
    navigator = rememberCircuitNavigator(navStack = navStack, onRootPop = {}),
    interceptors = state.navigationInterceptors.toList(),
    eventListeners = state.navigationEventInterceptors.toList()
  )

  val options = Navigator.StateOptions.SaveAndRestore

  LaunchedEffect(state.selectedTab) {
    when (state.selectedTab) {
      BottomBarTabs.HOME -> interceptedNavigator.resetRoot(HomeScreens.Home, options)
      BottomBarTabs.LENDS -> interceptedNavigator.resetRoot(LendScreens.LendHome, options)
      BottomBarTabs.GROUPS -> interceptedNavigator.resetRoot(GroupsScreens.GroupsHome, options)
      BottomBarTabs.PROFILE -> interceptedNavigator.resetRoot(ProfileScreens.ProfileHome, options)
    }
  }


  AdaptiveScaffold(
    modifier = modifier,
    bottomBar = {
      AdaptiveNavigationBar(
        modifier = Modifier.fillMaxWidth(),
        iosItems = BottomBarTabs.entries.map {
          UIKitUITabBarItem(
            title = it.label,
            image = UIKitImage.Vector(it.icon)
          )
        },
        iosSelectedIndex = BottomBarTabs.entries.indexOf(state.selectedTab),
        iosOnItemSelected = { selectedIndex ->
          state.dispatch(
            AppEvents.SelectBottomBarTab(BottomBarTabs.entries[selectedIndex])
          )
        }
      ) {
        BottomBarTabs.entries.forEach {
          NavigationBarItem(
            selected = state.selectedTab == it,
            onClick = { state.dispatch(AppEvents.SelectBottomBarTab(it)) },
            label = {
              Text(it.label)
            },
            icon = {
              Icon(it.icon, contentDescription = it.label)
            }
          )
        }
      }
    }
  ) {
    NavigableCircuitContent(
      modifier = Modifier.padding(it),
      navigator = interceptedNavigator,
      navStack = navStack,
      decoratorFactory =
        remember(interceptedNavigator) {
          GestureNavigationDecorationFactory()
        }
    )

  }
}
