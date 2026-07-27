package dev.dwak.lender.feature.auth.presenter.signup

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.app.modification.SignUpUserMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.feature.auth.navigation.api.AuthScreens
import dev.dwak.lender.lender_app.coroutines.Io
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@CircuitInject(
  screen = AuthScreens.SignUp::class,
  scope = AppScope::class
)
@Inject
class SignUpPresenter(
  private val dataModifier: DataModifier,
  @Io private val ioScope: CoroutineScope,
) : Presenter<SignUpState>{
  @Composable
  override fun present(): SignUpState {
    val firstName = rememberTextFieldState()
    val lastName = rememberTextFieldState()
    val username = rememberTextFieldState()
    val password = rememberTextFieldState()
    val confirmPassword = rememberTextFieldState()
    val inviteCode = rememberTextFieldState()
    return SignUpState(
      firstName = firstName,
      lastName = lastName,
      username = username,
      password = password,
      confirmPassword = confirmPassword,
      inviteCode = inviteCode,
    ) {
      when (it) {
        SignUpEvents.SignUp -> {
          ioScope.launch {
            when(dataModifier.submit(
              SignUpUserMod(
                email = username.text.toString(),
                password = password.text.toString(),
                confirmPassword = confirmPassword.text.toString(),
                firstName = firstName.text.toString(),
                lastName = lastName.text.toString(),
                inviteLinkToken = inviteCode.text.toString(),
              )
            )) {
              SignUpUserMod.Result.Error -> {}
              SignUpUserMod.Result.Success -> {}
            }
          }
        }
      }
    }
  }
}