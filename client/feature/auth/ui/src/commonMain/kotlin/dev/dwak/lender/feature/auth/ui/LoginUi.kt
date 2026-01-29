package dev.dwak.lender.feature.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.auth.navigation.api.AuthRoutes
import dev.dwak.lender.feature.auth.presenter.LoginState
import dev.zacsweers.metro.AppScope

@Composable
fun LoginUi() {
}

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
      val username = rememberTextFieldState()
      val password = rememberTextFieldState()
      Text("Username")
      TextField(username)
      Text("Password")
      TextField(password)
    }
  }
}