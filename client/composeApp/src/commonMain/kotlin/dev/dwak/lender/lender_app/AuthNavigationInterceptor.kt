package dev.dwak.lender.lender_app

import com.slack.circuit.runtime.screen.Screen
import com.slack.circuitx.navigation.intercepting.InterceptedGoToResult
import com.slack.circuitx.navigation.intercepting.NavigationContext
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import dev.dwak.lender.app.navigation.AuthenticatedLenderScreen
import dev.dwak.lender.feature.auth.navigation.api.AuthRoutes
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.repos.client.UserRepo
import dev.dwak.models.client.ClientUser
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.SingleIn
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@ContributesIntoSet(scope = AppScope::class)
@SingleIn(AppScope::class)
class AuthNavigationInterceptor(
  private val userRepo: UserRepo,
  @Io private val ioScope: CoroutineScope
) : NavigationInterceptor {
  private var isLoggedIn = false
  init {
    ioScope.launch {
      userRepo.currentUser().map { it is ClientUser.LoggedIn }.collect { isLoggedIn = it }
    }
  }

  override fun goTo(screen: Screen, navigationContext: NavigationContext): InterceptedGoToResult {
    if (screen is AuthenticatedLenderScreen && !isLoggedIn) {
      return InterceptedGoToResult.Rewrite(AuthRoutes.Launch)
    }
    return super.goTo(screen, navigationContext)
  }
}