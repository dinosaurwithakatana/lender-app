package dev.dwak.lender.feature.home.presenter

import androidx.compose.runtime.Composable
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.feature.home.navigation.HomeRoutes
import dev.zacsweers.metro.AppScope

@CircuitInject(
  screen = HomeRoutes.Home::class,
  scope = AppScope::class
)
class HomePresenter : Presenter<HomeState>{
  @Composable
  override fun present(): HomeState {
    return HomeState() {

    }
  }
}