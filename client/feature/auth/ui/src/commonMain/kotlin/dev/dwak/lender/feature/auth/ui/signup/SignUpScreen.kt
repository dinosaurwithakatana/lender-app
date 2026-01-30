package dev.dwak.lender.feature.auth.ui.signup

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
import dev.dwak.lender.feature.auth.presenter.signup.SignUpEvents
import dev.dwak.lender.feature.auth.presenter.signup.SignUpState
import dev.zacsweers.metro.AppScope

@CircuitInject(
  screen = AuthRoutes.SignUp::class,
  scope = AppScope::class
)
class SignUpScreen : Ui<SignUpState>{
  @Composable
  override fun Content(
    state: SignUpState,
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
      Button(onClick = {
        state.dispatch(SignUpEvents.SignUp)
      }) {
        Text("Sign Up")
      }
    }

  }
}