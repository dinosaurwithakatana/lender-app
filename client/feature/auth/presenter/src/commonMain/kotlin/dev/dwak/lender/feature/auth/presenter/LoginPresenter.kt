package dev.dwak.lender.feature.auth.presenter

import androidx.compose.runtime.Composable
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.feature.auth.navigation.api.AuthRoutes
import dev.zacsweers.metro.AppScope


@CircuitInject(
  screen = AuthRoutes.Login::class,
  scope = AppScope::class
)
class LoginPresenter(): Presenter<LoginState> {
  @Composable
  override fun present(): LoginState {
    return LoginState() { event ->
      when (event) {
        LoginEvents.Login -> TODO()
      }
    }
  }
}