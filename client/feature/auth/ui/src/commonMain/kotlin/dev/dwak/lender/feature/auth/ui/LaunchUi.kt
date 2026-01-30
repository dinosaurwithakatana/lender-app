package dev.dwak.lender.feature.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.auth.navigation.api.AuthRoutes
import dev.dwak.lender.feature.auth.presenter.launch.LaunchEvents
import dev.dwak.lender.feature.auth.presenter.launch.LaunchState
import dev.zacsweers.metro.AppScope

@CircuitInject(
  screen = AuthRoutes.Launch::class,
  scope = AppScope::class
)
class LaunchUi : Ui<LaunchState> {
  @Composable
  override fun Content(
    state: LaunchState,
    modifier: Modifier
  ) {
    Column(
      modifier = modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Button(onClick = {
        state.dispatch(LaunchEvents.GoToLogin)
      }) {
        Text("Login")
      }
      Button(onClick = {
        state.dispatch(LaunchEvents.GoToSignUp)
      }) {
        Text("Sign Up")
      }
    }
  }

}