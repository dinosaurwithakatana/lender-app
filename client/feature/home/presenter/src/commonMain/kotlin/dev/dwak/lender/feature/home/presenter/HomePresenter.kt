package dev.dwak.lender.feature.home.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.app.modification.Logout
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.feature.home.navigation.HomeRoutes
import dev.dwak.lender.lender_app.coroutines.Io
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@CircuitInject(
  screen = HomeRoutes.Home::class,
  scope = AppScope::class
)
@Inject
class HomePresenter(
  private val dataModifier: DataModifier,
  @Io private val ioScope: CoroutineScope,
) : Presenter<HomeState> {
  @Composable
  override fun present(): HomeState {
    return HomeState() {
      when (it) {
        HomeEvents.Logout -> {
          ioScope.launch {
            dataModifier.submit(Logout)
          }
        }
      }
    }
  }
}