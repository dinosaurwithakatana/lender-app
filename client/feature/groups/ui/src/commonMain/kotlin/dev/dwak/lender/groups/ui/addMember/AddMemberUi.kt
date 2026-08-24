package dev.dwak.lender.groups.ui.addMember

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import dev.dwak.lender.icons.arrow_back
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
import dev.dwak.lender.groups.presenter.addMember.AddMemberEvents
import dev.dwak.lender.groups.presenter.addMember.AddMemberState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

@CircuitInject(
  screen = GroupsScreens.AddMember::class,
  scope = AppScope::class,
)
@Inject
class AddMemberUi : Ui<AddMemberState> {
  @OptIn(ExperimentalMaterial3Api::class, ExperimentalCalfUiApi::class)
  @Composable
  override fun Content(
    state: AddMemberState,
    modifier: Modifier,
  ) {
    AdaptiveScaffold(
      modifier = modifier,
      topBar = {
        AdaptiveTopBar(
          title = { Text("Add Member") },
          navigationIcon = {
            IconButton(onClick = { state.dispatch(AddMemberEvents.Back) }) {
              Icon(imageVector = arrow_back, contentDescription = "Back")
            }
          },
          iosTitle = "Add Member",
          iosLeadingItems = listOf(
            UIKitUIBarButtonItem.title(
              title = "Back",
              onClick = { state.dispatch(AddMemberEvents.Back) },
            )
          ),
        )
      },
    ) { padding ->
      AddMember(
        modifier = Modifier.padding(padding),
        state = state,
      )
    }
  }
}

@Composable
private fun AddMember(
  modifier: Modifier = Modifier,
  state: AddMemberState,
) {
  Column(
    modifier = modifier.fillMaxSize().padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    TextField(
      state = state.email,
      label = { Text("Email") },
    )
    Button(onClick = { state.dispatch(AddMemberEvents.Search) }) {
      Text("Search")
    }

    when (val result = state.lookup) {
      AddMemberState.LookupResult.Idle -> Unit
      AddMemberState.LookupResult.Searching -> CircularProgressIndicator()
      AddMemberState.LookupResult.NotFound -> Text(
        text = "No user found with that email.",
        style = MaterialTheme.typography.bodyMedium,
      )
      is AddMemberState.LookupResult.Found -> {
        Text(
          text = "${result.profile.firstName} ${result.profile.lastName}",
          style = MaterialTheme.typography.titleMedium,
        )
        Button(
          onClick = { state.dispatch(AddMemberEvents.Invite) },
          enabled = !state.submitting,
        ) {
          Text(if (state.submitting) "Inviting…" else "Invite")
        }
      }
    }

    val error = state.errorMessage
    if (error != null) {
      Text(
        text = error,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodyMedium,
      )
    }
  }
}
