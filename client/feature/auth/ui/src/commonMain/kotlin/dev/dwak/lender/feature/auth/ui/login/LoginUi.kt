package dev.dwak.lender.feature.auth.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.auth.navigation.api.AuthRoutes
import dev.dwak.lender.feature.auth.presenter.login.LoginEvents
import dev.dwak.lender.feature.auth.presenter.login.LoginState
import dev.zacsweers.metro.AppScope

@CircuitInject(
  screen = AuthRoutes.Login::class,
  scope = AppScope::class
)
class LoginScreen() : Ui<LoginState> {
  @Composable
  override fun Content(
    state: LoginState,
    modifier: Modifier
  ) {
    Column(
      modifier = modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text("Username")
      TextField(state.username)
      Text("Password")
      TextField(state.password)
      Button(onClick = {
        state.dispatch(LoginEvents.Login)
      }) {
        Text("Login")
      }
    }
  }
}