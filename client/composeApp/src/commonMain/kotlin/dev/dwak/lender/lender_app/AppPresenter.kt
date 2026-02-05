package dev.dwak.lender.lender_app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import dev.dwak.lender.repos.client.UserRepo
import dev.dwak.models.client.ClientUser
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.filterIsInstance

@CircuitInject(AppScreen::class, AppScope::class)
@Inject
class AppPresenter(
  private val navigationInterceptors: Set<NavigationInterceptor>,
  private val userRepo: UserRepo,
) : Presenter<AppState> {
  @Composable
  override fun present(): AppState {
    var isLoggedIn by remember { mutableStateOf(false) }
    val state by produceState(
      AppState(
        navigationInterceptors = navigationInterceptors,
        isLoggedIn = userRepo.currentUser().value is ClientUser.LoggedIn,
        { event -> }
      ),
      {
        userRepo.currentUser().collect {
          value = value.copy(
            isLoggedIn = it is ClientUser.LoggedIn
          )
        }
      }
    )
    return state
  }
}