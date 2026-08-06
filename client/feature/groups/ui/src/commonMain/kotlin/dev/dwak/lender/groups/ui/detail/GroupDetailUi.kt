package dev.dwak.lender.groups.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.calf.ui.ExperimentalCalfUiApi
import com.mohamedrejeb.calf.ui.navigation.AdaptiveScaffold
import com.mohamedrejeb.calf.ui.navigation.AdaptiveTopBar
import com.mohamedrejeb.calf.ui.navigation.UIKitUIBarButtonItem
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.groups.navigation.GroupsScreens
import dev.dwak.lender.groups.presenter.detail.GroupDetailEvents
import dev.dwak.lender.groups.presenter.detail.GroupDetailState
import dev.dwak.models.client.ClientMembership
import dev.dwak.models.client.ClientMembershipStatus
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

@CircuitInject(
  screen = GroupsScreens.GroupDetail::class,
  scope = AppScope::class,
)
@Inject
class GroupDetailUi : Ui<GroupDetailState> {
  @OptIn(ExperimentalMaterial3Api::class, ExperimentalCalfUiApi::class)
  @Composable
  override fun Content(
    state: GroupDetailState,
    modifier: Modifier,
  ) {
    AdaptiveScaffold(
      modifier = modifier,
      topBar = {
        AdaptiveTopBar(
          iosTitle = state.detail?.group?.name ?: "Group",
          iosLeadingItems = listOf(
            UIKitUIBarButtonItem.title(
              title = "Back",
              onClick = {
                state.dispatch(GroupDetailEvents.Back)
              },
            )
          ),
        )
      },
    ) { padding ->
      GroupDetail(
        modifier = Modifier.padding(padding),
        state = state,
      )
    }
  }

  @Composable
  private fun GroupDetail(
    modifier: Modifier = Modifier,
    state: GroupDetailState,
  ) {
    val detail = state.detail
    if (detail == null) {
      Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        if (state.loading) CircularProgressIndicator()
      }
      return
    }

    LazyColumn(modifier = modifier.fillMaxSize()) {
      item {
        Text(
          modifier = Modifier.padding(16.dp),
          text = "Members",
          style = MaterialTheme.typography.titleMedium,
        )
      }
      items(detail.memberships, key = { it.id.id }) { membership ->
        MembershipRow(membership)
        HorizontalDivider()
      }
    }
  }

  @Composable
  private fun MembershipRow(membership: ClientMembership) {
    ListItem(
      headlineContent = {
        Text("${membership.profile.firstName} ${membership.profile.lastName}")
      },
      supportingContent = {
        Text(membership.status.label())
      },
    )
  }

  private fun ClientMembershipStatus.label(): String = when (this) {
    ClientMembershipStatus.OWNER -> "Owner"
    ClientMembershipStatus.APPROVED -> "Member"
    ClientMembershipStatus.REQUESTED -> "Requested"
  }
}
