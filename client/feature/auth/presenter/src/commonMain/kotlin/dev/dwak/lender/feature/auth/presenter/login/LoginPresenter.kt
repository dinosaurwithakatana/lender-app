package dev.dwak.lender.feature.auth.presenter.login

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.app.modification.LoginUser
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.feature.auth.navigation.api.AuthRoutes
import dev.dwak.lender.lender_app.coroutines.Io
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@CircuitInject(
  screen = AuthRoutes.Login::class,
  scope = AppScope::class
)
@Inject
class LoginPresenter(
  private val dataModifier: DataModifier,
) : Presenter<LoginState> {
  @Composable
  override fun present(): LoginState {
    val scope = rememberCoroutineScope()
    val username = rememberTextFieldState()
    val password = rememberTextFieldState()
    return LoginState(
      username = username,
      password = password,
      dispatch = { event ->
        when (event) {
          LoginEvents.Login -> {
            scope.launch {
              dataModifier.submit(
                LoginUser(
                  email = username.text.toString(),
                  password = password.text.toString()
                )
              )
            }
          }
        }
      }
    )
  }
}