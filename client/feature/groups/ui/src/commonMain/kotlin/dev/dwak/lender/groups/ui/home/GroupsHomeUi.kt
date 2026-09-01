package dev.dwak.lender.groups.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mohamedrejeb.calf.ui.navigation.AdaptiveScaffold
import com.mohamedrejeb.calf.ui.navigation.AdaptiveTopBar
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.client.feature.groups.ui.ui.generated.resources.Res
import dev.dwak.lender.feature.groups.navigation.GroupsScreens
import dev.dwak.lender.groups.presenter.home.GroupsHomeEvents
import dev.dwak.lender.groups.presenter.home.GroupsHomeState
import dev.dwak.lender.icons.add
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import org.jetbrains.compose.resources.painterResource

@CircuitInject(
  screen = GroupsScreens.GroupsHome::class,
  scope = AppScope::class
)
@Inject
class GroupsHomeUi : Ui<GroupsHomeState> {

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  override fun Content(
    state: GroupsHomeState,
    modifier: Modifier
  ) {

    AdaptiveScaffold(
      modifier = modifier,
      topBar = {
        AdaptiveTopBar(
          title = { Text("Groups") },
          iosTitle = "Groups",
        )
      },
      floatingActionButton = {
        FloatingActionButton(
          onClick = {
            state.dispatch(GroupsHomeEvents.CreateGroup)
          },
        ) {
          Icon(add, contentDescription = null)
        }
      }
    ) {
      GroupsHome(modifier = Modifier.padding(it), state = state)
    }
  }

  @Composable
  private fun GroupsHome(
    modifier: Modifier = Modifier,
    state: GroupsHomeState,
  ) {
    Column(modifier.fillMaxSize()) {
      state.groups.forEach { group ->
        ListItem(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { state.dispatch(GroupsHomeEvents.OpenGroup(group.id)) },
          headlineContent = {
            Text(text = group.name)
          },
        )
        HorizontalDivider()
      }
    }
  }
}