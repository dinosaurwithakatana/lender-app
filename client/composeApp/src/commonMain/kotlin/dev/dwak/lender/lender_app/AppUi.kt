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
import dev.dwak.lender.feature.auth.navigation.api.AuthRoutes
import dev.dwak.lender.feature.home.navigation.HomeRoutes
import dev.zacsweers.metro.AppScope

@CircuitInject(
  screen = AppScreen::class,
  scope = AppScope::class
)
@Composable
fun AppUi(
  state: AppState,
  modifier: Modifier = Modifier.Companion
) {
  val navStack = rememberSaveableNavStack(root = if (state.isLoggedIn) HomeRoutes.Home else AuthRoutes.Launch)
  val interceptedNavigator = rememberInterceptingNavigator(
    navigator = rememberCircuitNavigator(navStack) {
      // Do something when the root screen is popped, usually exiting the app
    },
    interceptors = state.navigationInterceptors.toList()
  )
  NavigableCircuitContent(
    modifier = modifier,
    navigator = interceptedNavigator,
    navStack = navStack,
    decoratorFactory =
      remember(interceptedNavigator) {
        GestureNavigationDecorationFactory(
          // Pop the back stack once the user has gone 'back'
          onBackInvoked = interceptedNavigator::pop
        )
      }
  )
}