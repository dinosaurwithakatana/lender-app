package dev.dwak.lender.feature.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.home.navigation.HomeRoutes
import dev.dwak.lender.feature.home.presenter.HomeEvents
import dev.dwak.lender.feature.home.presenter.HomeState
import dev.zacsweers.metro.AppScope

@CircuitInject(
  screen = HomeRoutes.Home::class,
  scope = AppScope::class
)
class HomeUi : Ui<HomeState> {
  @Composable
  override fun Content(
    state: HomeState,
    modifier: Modifier
  ) {
    Column {
      Text("Home")
      Button(onClick = { state.dispatch(HomeEvents.Logout) }) {
        Text("Logout")
      }
    }
  }
}