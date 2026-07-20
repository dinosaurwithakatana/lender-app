package dev.dwak.lender.feature.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mohamedrejeb.calf.ui.button.AdaptiveButton
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.home.navigation.HomeScreens
import dev.dwak.lender.feature.home.presenter.HomeEvents
import dev.dwak.lender.feature.home.presenter.HomeState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

@CircuitInject(
  screen = HomeScreens.Home::class,
  scope = AppScope::class
)
@Inject
class HomeUi : Ui<HomeState> {
  @Composable
  override fun Content(
    state: HomeState,
    modifier: Modifier
  ) {
    Home(state)
  }
}

@Composable
fun Home(
  state: HomeState
) {
  Column(modifier = Modifier.fillMaxSize()) {
    Text("Home")
    AdaptiveButton(onClick = { state.dispatch(HomeEvents.NavigateToCreateItem) }) {
      Text("Create Item")
    }
    AdaptiveButton(onClick = { state.dispatch(HomeEvents.Logout) }) {
      Text("Logout")
    }
  }
}

@Preview
@Composable
fun HomePreview() {
  Home(
    state = HomeState(
      dispatch = {}
    ))
}