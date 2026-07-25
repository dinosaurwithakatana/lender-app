package dev.dwak.lender.lender_app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.produceRetainedState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuitx.navigation.intercepting.LoggingNavigationEventListener
import com.slack.circuitx.navigation.intercepting.NavigationEventListener
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import com.slack.circuitx.navigation.intercepting.NavigationLogger
import dev.dwak.lender.feature.groups.navigation.GroupsScreens
import dev.dwak.lender.feature.home.navigation.HomeScreens
import dev.dwak.lender.repos.client.UserRepo
import dev.dwak.models.client.ClientUser
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.Inject
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.filterIsInstance

@AssistedInject
class AppPresenter(
  private val navigationInterceptors: Set<NavigationInterceptor>,
  private val userRepo: UserRepo,
  @Assisted private val navigator: Navigator,
) : Presenter<AppState> {
  @Composable
  override fun present(): AppState {
    val currentUser by userRepo.currentUser().collectAsRetainedState(ClientUser.Loading)
    LaunchedEffect(currentUser) {
      Napier.d { "Current user: $currentUser" }
    }
    var currentTab by remember { mutableStateOf(BottomBarTabs.HOME) }
    return AppState(
      navigationInterceptors = navigationInterceptors,
      navigationEventInterceptors = setOf(
        LoggingNavigationEventListener(logger = object : NavigationLogger {
          override fun log(message: String) {
            Napier.d { message }
          }
        })
      ),
      isLoggedIn = currentUser is ClientUser.LoggedIn,
      loading = currentUser is ClientUser.Loading,
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

  @CircuitInject(AppScreen::class, AppScope::class)
  @AssistedFactory
  fun interface Factory {
    fun create(navigator: Navigator): AppPresenter
  }
}