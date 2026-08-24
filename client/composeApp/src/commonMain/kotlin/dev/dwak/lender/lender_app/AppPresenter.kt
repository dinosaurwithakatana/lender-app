package dev.dwak.lender.lender_app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuitx.navigation.intercepting.LoggingNavigationEventListener
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import com.slack.circuitx.navigation.intercepting.NavigationLogger
import dev.dwak.lender.lender_app.tabs.BottomBarTabs
import dev.dwak.lender.repos.client.ProfileRepo
import dev.dwak.lender.repos.client.RepoRefresher
import dev.dwak.lender.repos.client.ServerConfigRepo
import dev.dwak.lender.repos.client.ServerConfigState
import dev.dwak.lender.repos.client.UserRepo
import dev.dwak.models.client.ClientUser
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import io.github.aakira.napier.Napier

@AssistedInject
class AppPresenter(
  private val navigationInterceptors: Set<NavigationInterceptor>,
  private val userRepo: UserRepo,
  private val serverConfigRepo: ServerConfigRepo,
  private val profileRefresher: RepoRefresher<ProfileRepo.RefreshTypes>,
  @Assisted private val navigator: Navigator,
) : Presenter<AppState> {
  @Composable
  override fun present(): AppState {
    val serverState by serverConfigRepo.state.collectAsRetainedState()

    return when (serverState) {
      ServerConfigState.Loading -> AppState.Loading
      ServerConfigState.Unconfigured -> AppState.NeedsServerConfig
      is ServerConfigState.Configured -> presentUser()
    }
  }

  @Composable
  private fun presentUser(): AppState {
    val currentUser by userRepo.currentUser().collectAsRetainedState(ClientUser.Loading)

    return when (currentUser) {
      ClientUser.Loading -> AppState.Loading
      ClientUser.LoggedOut -> AppState.LoggedOut
      is ClientUser.LoggedIn -> {
        var currentTab by remember { mutableStateOf(BottomBarTabs.HOME) }
        LaunchedEffect(currentUser) {
          profileRefresher.refresh(ProfileRepo.RefreshTypes.CurrentProfile)
        }
        AppState.LoggedIn(
          navigationInterceptors = navigationInterceptors,
          navigationEventInterceptors = setOf(
            LoggingNavigationEventListener(logger = object : NavigationLogger {
              override fun log(message: String) {
                Napier.d { message }
              }
            })
          ),
          selectedTab = currentTab,
          dispatch = { event ->
            when (event) {
              is AppEvents.SelectBottomBarTab -> {
                currentTab = event.tab
              }
            }
          }
        )
      }
    }
  }

  @CircuitInject(AppScreen::class, AppScope::class)
  @AssistedFactory
  fun interface Factory {
    fun create(navigator: Navigator): AppPresenter
  }
}
