package dev.dwak.lender.groups.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.groups.navigation.GroupsScreens
import dev.dwak.lender.groups.presenter.home.GroupsHomeState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

@CircuitInject(
  screen = GroupsScreens.GroupsHome::class,
  scope = AppScope::class
)
@Inject
class GroupsHomeUi: Ui<GroupsHomeState> {

  @Composable
  override fun Content(
    state: GroupsHomeState,
    modifier: Modifier
  ) {
    Text("Groups")
  }

}