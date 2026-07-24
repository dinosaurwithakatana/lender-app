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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.calf.ui.ExperimentalCalfUiApi
import com.mohamedrejeb.calf.ui.button.AdaptiveButton
import com.mohamedrejeb.calf.ui.navigation.AdaptiveScaffold
import com.mohamedrejeb.calf.ui.navigation.AdaptiveTopBar
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.home.navigation.HomeScreens
import dev.dwak.lender.feature.home.presenter.HomeEvents
import dev.dwak.lender.feature.home.presenter.HomeState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import org.jetbrains.compose.resources.stringResource

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
          iosTitle = "Lender"
        )
      }
    ) {
      Home(
        state = state,
        contentPadding = it
      )
    }
  }
}

@Composable
fun Home(
  modifier: Modifier = Modifier,
  state: HomeState,
  contentPadding: PaddingValues = PaddingValues(),
) {
  PullToRefreshBox(
    modifier = modifier.fillMaxSize().padding(contentPadding).padding(horizontal = 16.dp),
    isRefreshing = state.refreshing,
    onRefresh = {
      state.dispatch(HomeEvents.Refresh)
    }
  ) {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
      state.items.forEach {
        Column {
          Text(it.name, style = MaterialTheme.typography.headlineSmall)
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(it.description.orEmpty())
            Text("${it.availableQuantity} / ${it.totalQuantity}")
          }
        }
      }
      AdaptiveButton(onClick = { state.dispatch(HomeEvents.NavigateToCreateItem) }) {
        Text("Create Item")
      }
      AdaptiveButton(onClick = { state.dispatch(HomeEvents.Logout) }) {
        Text("Logout")
      }
    }
  }
}

@Preview
@Composable
fun HomePreview() {
  Home(
    state = HomeState(
      items = emptyList(),
      dispatch = {},
      loading = false,
      refreshing = false
    )
  )
}