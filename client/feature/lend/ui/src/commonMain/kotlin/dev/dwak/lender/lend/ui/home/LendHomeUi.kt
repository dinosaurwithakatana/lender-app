package dev.dwak.lender.lend.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mohamedrejeb.calf.ui.navigation.AdaptiveScaffold
import com.mohamedrejeb.calf.ui.navigation.AdaptiveTopBar
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.lend.navigation.LendScreens
import dev.dwak.lender.icons.add
import dev.dwak.lender.lend.presenter.home.LendHomeEvents
import dev.dwak.lender.lend.presenter.home.LendHomeState
import dev.dwak.models.client.ClientLend
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

@CircuitInject(
  screen = LendScreens.LendHome::class,
  scope = AppScope::class
)
@Inject
class LendHomeUi : Ui<LendHomeState> {

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  override fun Content(
    state: LendHomeState,
    modifier: Modifier
  ) {
    AdaptiveScaffold(
      modifier = modifier,
      topBar = {
        AdaptiveTopBar(
          iosTitle = "Lends"
        )
      },
      floatingActionButton = {
        FloatingActionButton(
          onClick = { state.dispatch(LendHomeEvents.AddLend) },
        ) {
          Icon(add, contentDescription = null)
        }
      }
    ) {
      LendHome(
        modifier = Modifier.padding(it),
        state = state,
      )
    }
  }

  @Composable
  private fun LendHome(
    modifier: Modifier = Modifier,
    state: LendHomeState,
  ) {
    Column(modifier = modifier.fillMaxSize()) {
      state.lends.forEach { lend ->
        Text(text = lend.displayLine())
      }
    }
  }
}

private fun ClientLend.displayLine(): String {
  val arrow = when (direction) {
    ClientLend.Direction.OUTGOING -> "→"
    ClientLend.Direction.INCOMING -> "←"
  }
  return "$itemName · qty $quantity · $status $arrow $counterpartyFirstName $counterpartyLastName"
}
