package dev.dwak.lender.groups.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mohamedrejeb.calf.ui.ExperimentalCalfUiApi
import com.mohamedrejeb.calf.ui.navigation.AdaptiveScaffold
import com.mohamedrejeb.calf.ui.navigation.AdaptiveTopBar
import com.mohamedrejeb.calf.ui.navigation.UIKitUIBarButtonItem
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.groups.navigation.GroupsScreens
import dev.dwak.lender.groups.presenter.create.CreateGroupEvents
import dev.dwak.lender.groups.presenter.create.CreateGroupState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

@CircuitInject(
  screen = GroupsScreens.CreateGroup::class,
  scope = AppScope::class
)
@Inject
class CreateGroupUi : Ui<CreateGroupState> {
  @OptIn(ExperimentalMaterial3Api::class, ExperimentalCalfUiApi::class)
  @Composable
  override fun Content(
    state: CreateGroupState,
    modifier: Modifier
  ) {
    AdaptiveScaffold(
      modifier = modifier,
      topBar = {
        AdaptiveTopBar(
          iosTitle = "Create Group",
          iosLeadingItems = listOf(
            UIKitUIBarButtonItem.title(
              title = "Back",
              onClick = {
                state.dispatch(CreateGroupEvents.Back)
              }
            )
          )
        )
      },
    ) {
      CreateGroup(
        modifier = Modifier.padding(it),
        state = state
      )
    }
  }
}

@Composable
fun CreateGroup(
  modifier: Modifier = Modifier,
  state: CreateGroupState
) {
  Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
    TextField(
      state = state.name,
      label = {
        Text("Name")
      }
    )
    Button(onClick = {
      state.dispatch(CreateGroupEvents.AttemptSave)
    }) {
      Text("Save")
    }
  }
}

@Composable
@Preview
fun CreateGroupPreview() {
  CreateGroup(
    state = CreateGroupState(
      name = TextFieldState(),
      dispatch = {}
    )
  )
}
