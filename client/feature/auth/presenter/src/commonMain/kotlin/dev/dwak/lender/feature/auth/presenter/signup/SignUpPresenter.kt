package dev.dwak.lender.feature.auth.presenter.signup

import androidx.compose.runtime.Composable
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.feature.auth.navigation.api.AuthRoutes
import dev.zacsweers.metro.AppScope

@CircuitInject(
  screen = AuthRoutes.SignUp::class,
  scope = AppScope::class
)
class SignUpPresenter : Presenter<SignUpState>{
  @Composable
  override fun present(): SignUpState {
    return SignUpState() {

    }
  }
}