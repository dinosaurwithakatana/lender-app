package dev.dwak.lender.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.calf.ui.ExperimentalCalfUiApi
import com.mohamedrejeb.calf.ui.button.AdaptiveButton
import com.mohamedrejeb.calf.ui.navigation.AdaptiveScaffold
import com.mohamedrejeb.calf.ui.navigation.AdaptiveTopBar
import com.mohamedrejeb.calf.ui.navigation.UIKitNavigationBarConfiguration
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.home.navigation.HomeScreens
import dev.dwak.lender.feature.home.presenter.HomeEvents
import dev.dwak.lender.feature.home.presenter.HomeState
import dev.dwak.lender.icons.add
import dev.dwak.models.client.ClientItem
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

@CircuitInject(
  screen = HomeScreens.Home::class,
  scope = AppScope::class
)
@Inject
class HomeUi : Ui<HomeState> {
  @OptIn(ExperimentalMaterial3Api::class, ExperimentalCalfUiApi::class)
  @Composable
  override fun Content(
    state: HomeState,
    modifier: Modifier
  ) {
    AdaptiveScaffold(
      modifier = modifier,
      topBar = {
        AdaptiveTopBar(
          iosTitle = "Lender",
        )
      },
      floatingActionButton = {
        FloatingActionButton(
          onClick = { state.dispatch(HomeEvents.NavigateToCreateItem) },
        ) {
          Icon(add, contentDescription = "Create item")
        }
      },
    ) {
      Home(
        state = state,
        contentPadding = it,
      )
    }

    state.itemPendingDelete?.let { item ->
      DeleteItemDialog(
        item = item,
        onConfirm = { state.dispatch(HomeEvents.ConfirmDeleteItem) },
        onDismiss = { state.dispatch(HomeEvents.CancelDeleteItem) },
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
  modifier: Modifier = Modifier,
  state: HomeState,
  contentPadding: PaddingValues = PaddingValues(),
) {
  PullToRefreshBox(
    modifier = modifier.fillMaxSize().padding(contentPadding).padding(horizontal = 16.dp),
    isRefreshing = state.refreshing,
    onRefresh = { state.dispatch(HomeEvents.Refresh) },
  ) {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
      state.items.forEach { item ->
        ItemRow(
          item = item,
          onDelete = { state.dispatch(HomeEvents.RequestDeleteItem(item)) },
        )
        HorizontalDivider()
      }
    }
  }
}

@Composable
private fun ItemRow(
  item: ClientItem,
  onDelete: () -> Unit,
) {
  Row(
    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(item.name, style = MaterialTheme.typography.headlineSmall)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        Text(item.description.orEmpty())
        Text("${item.availableQuantity} / ${item.totalQuantity}")
      }
    }
    TextButton(onClick = onDelete) { Text("Delete") }
  }
}

@Composable
private fun DeleteItemDialog(
  item: ClientItem,
  onConfirm: () -> Unit,
  onDismiss: () -> Unit,
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("Delete \"${item.name}\"?") },
    text = { Text("This will also remove any lends of this item.") },
    confirmButton = {
      TextButton(onClick = onConfirm) { Text("Delete") }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) { Text("Cancel") }
    },
  )
}

@Preview
@Composable
fun HomePreview() {
  Home(
    state = HomeState(
      items = emptyList(),
      itemPendingDelete = null,
      dispatch = {},
      loading = false,
      refreshing = false,
    )
  )
}
