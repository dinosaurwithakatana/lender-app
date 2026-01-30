package dev.dwak.lender.lender_app

import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.PopResult
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuitx.navigation.intercepting.InterceptedGoToResult
import com.slack.circuitx.navigation.intercepting.InterceptedPopResult
import com.slack.circuitx.navigation.intercepting.InterceptedResetRootResult
import com.slack.circuitx.navigation.intercepting.NavigationContext
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(scope = AppScope::class)
class AuthNavigationInterceptor : NavigationInterceptor {

  override fun goTo(screen: Screen, navigationContext: NavigationContext): InterceptedGoToResult {
    return super.goTo(screen, navigationContext)
  }

  override fun pop(result: PopResult?, navigationContext: NavigationContext): InterceptedPopResult {
    return super.pop(result, navigationContext)
  }

  override fun resetRoot(
    newRoot: Screen,
    options: Navigator.StateOptions,
    navigationContext: NavigationContext
  ): InterceptedResetRootResult {
    return super.resetRoot(newRoot, options, navigationContext)
  }
}