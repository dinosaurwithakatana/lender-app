package dev.dwak.lender.lender_app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.navstack.rememberSaveableNavStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuitx.gesturenavigation.GestureNavigationDecorationFactory
import com.slack.circuitx.navigation.intercepting.rememberInterceptingNavigator
import dev.dwak.lender.feature.auth.navigation.api.AuthScreens
import dev.dwak.lender.feature.home.navigation.HomeScreens
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

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
    rememberSaveableNavStack(root = if (state.isLoggedIn) HomeScreens.Home else AuthScreens.Launch)

  val interceptedNavigator = rememberInterceptingNavigator(
    navigator = rememberCircuitNavigator(navStack = navStack, onRootPop = {}),
    interceptors = state.navigationInterceptors.toList(),
    eventListeners = state.navigationEventInterceptors.toList()
  )
  
  NavigableCircuitContent(
    modifier = modifier,
    navigator = interceptedNavigator,
    navStack = navStack,
    decoratorFactory =
      remember(interceptedNavigator) {
        GestureNavigationDecorationFactory(
          // Pop the back stack once the user has gone 'back'
        )
      }
  )
}