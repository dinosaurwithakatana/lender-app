package dev.dwak.lender.feature.auth.presenter.launch

import androidx.compose.runtime.Composable
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.feature.auth.navigation.api.AuthScreens
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject

@AssistedInject
class LaunchPresenter(
  @Assisted private val navigator: Navigator
) : Presenter<LaunchState>{
  @Composable
  override fun present(): LaunchState {
    return LaunchState() { event ->
      when (event) {
        LaunchEvents.GoToLogin -> navigator.goTo(AuthScreens.Login)
        LaunchEvents.GoToSignUp -> navigator.goTo(AuthScreens.SignUp)
      }
    }
  }

  @CircuitInject(
    screen = AuthScreens.Launch::class,
    scope = AppScope::class
  )
  @AssistedFactory
  interface Factory {
    fun create(navigator: Navigator): LaunchPresenter
  }
}