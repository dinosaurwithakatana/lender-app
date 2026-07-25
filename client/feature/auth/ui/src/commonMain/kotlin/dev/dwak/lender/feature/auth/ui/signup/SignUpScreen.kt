package dev.dwak.lender.feature.auth.ui.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.auth.navigation.api.AuthScreens
import dev.dwak.lender.feature.auth.presenter.signup.SignUpEvents
import dev.dwak.lender.feature.auth.presenter.signup.SignUpState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

@CircuitInject(
  screen = AuthScreens.SignUp::class,
  scope = AppScope::class
)
@Inject
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
      Text("Username")
      TextField(state.username, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
      Text("Password")
      SecureTextField(state.password, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))
      Text("Confirm Password")
      SecureTextField(state.confirmPassword, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))
      Text("Confirm Password")
      Text("Invite Code")
      TextField(state.inviteCode)
      Button(onClick = {
        state.dispatch(SignUpEvents.SignUp)
      }) {
        Text("Sign Up")
      }
    }

  }
}