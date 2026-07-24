package dev.dwak.lender.lend.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.calf.ui.navigation.AdaptiveScaffold
import com.mohamedrejeb.calf.ui.navigation.AdaptiveTopBar
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.lend.navigation.LendScreens
import dev.dwak.lender.icons.add
import dev.dwak.lender.lend.presenter.home.LendActionKind
import dev.dwak.lender.lend.presenter.home.LendAdvanceKind
import dev.dwak.lender.lend.presenter.home.LendHomeEvents
import dev.dwak.lender.lend.presenter.home.LendHomeState
import dev.dwak.lender.lend.presenter.home.actionKind
import dev.dwak.lender.lend.presenter.home.advanceKind
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
        LendRow(
          lend = lend,
          onDelete = { state.dispatch(LendHomeEvents.DeleteLend(lend)) },
          onAdvance = { state.dispatch(LendHomeEvents.AdvanceLend(lend)) },
        )
        HorizontalDivider()
      }
    }
  }
}

@Composable
private fun LendRow(
  lend: ClientLend,
  onDelete: () -> Unit,
  onAdvance: () -> Unit,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(lend.itemName, style = MaterialTheme.typography.bodyLarge)
      Text(
        text = lend.detailLine(),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
      lend.advanceKind()?.let { advance ->
        TextButton(onClick = onAdvance) { Text(advance.label()) }
      }
      TextButton(onClick = onDelete) {
        Text(lend.status.actionKind().label())
      }
    }
  }
}

private fun ClientLend.detailLine(): String {
  val arrow = when (direction) {
    ClientLend.Direction.OUTGOING -> "→"
    ClientLend.Direction.INCOMING -> "←"
  }
  return "qty $quantity · $status $arrow $counterpartyFirstName $counterpartyLastName"
}

private fun LendActionKind.label(): String = when (this) {
  LendActionKind.DENY -> "Deny"
  LendActionKind.RETURN -> "Mark Returned"
  LendActionKind.DELETE -> "Delete"
}

private fun LendAdvanceKind.label(): String = when (this) {
  LendAdvanceKind.APPROVE -> "Approve"
  LendAdvanceKind.MARK_LENT -> "Mark Lent"
}
