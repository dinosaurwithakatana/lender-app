package dev.dwak.lender.feature.auth.presenter.launch

import androidx.compose.runtime.Composable
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.feature.auth.navigation.api.AuthRoutes
import dev.zacsweers.metro.AppScope

@CircuitInject(
  screen = AuthRoutes.Launch::class,
  scope = AppScope::class
)
class LaunchPresenter(
  private val navigator: Navigator
) : Presenter<LaunchState>{
  @Composable
  override fun present(): LaunchState {
    return LaunchState() { event ->
      when (event) {
        LaunchEvents.GoToLogin -> navigator.goTo(AuthRoutes.Login)
      }
    }
  }
}