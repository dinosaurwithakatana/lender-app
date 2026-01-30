package dev.dwak.lender.lender_app

import com.slack.circuit.runtime.screen.Screen
import com.slack.circuitx.navigation.intercepting.InterceptedGoToResult
import com.slack.circuitx.navigation.intercepting.NavigationContext
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import dev.dwak.lender.app.navigation.AuthenticatedLenderRoute
import dev.dwak.lender.feature.auth.navigation.api.AuthRoutes
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ContributesIntoSet(scope = AppScope::class)
class AuthNavigationInterceptor(
  val authState: StateFlow<Boolean> = MutableStateFlow(false)
) : NavigationInterceptor {

  override fun goTo(screen: Screen, navigationContext: NavigationContext): InterceptedGoToResult {
    if (screen is AuthenticatedLenderRoute && !authState.value) {
      return InterceptedGoToResult.Rewrite(AuthRoutes.Launch)
    }
    return super.goTo(screen, navigationContext)
  }
}