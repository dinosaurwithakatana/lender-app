package dev.dwak.lender.lend.ui.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.calf.ui.ExperimentalCalfUiApi
import com.mohamedrejeb.calf.ui.navigation.AdaptiveScaffold
import com.mohamedrejeb.calf.ui.navigation.AdaptiveTopBar
import com.mohamedrejeb.calf.ui.navigation.UIKitUIBarButtonItem
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.lend.navigation.LendScreens
import dev.dwak.lender.lend.presenter.create.CreateLendEvents
import dev.dwak.lender.lend.presenter.create.CreateLendState
import dev.dwak.lender.lend.presenter.create.LendMode
import dev.dwak.models.client.ClientGroup
import dev.dwak.models.client.ClientItem
import dev.dwak.models.client.ClientProfile
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

@CircuitInject(
  screen = LendScreens.CreateLend::class,
  scope = AppScope::class
)
@Inject
class CreateLendUi : Ui<CreateLendState> {

  @OptIn(ExperimentalMaterial3Api::class, ExperimentalCalfUiApi::class)
  @Composable
  override fun Content(
    state: CreateLendState,
    modifier: Modifier
  ) {
    AdaptiveScaffold(
      modifier = modifier,
      topBar = {
        AdaptiveTopBar(
          iosTitle = "Lend Item",
          iosLeadingItems = listOf(
            UIKitUIBarButtonItem.title(
              title = "Back",
              onClick = { state.dispatch(CreateLendEvents.Back) }
            )
          ),
        )
      },
    ) { padding ->
      CreateLend(modifier = Modifier.padding(padding), state = state)
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateLend(
  modifier: Modifier = Modifier,
  state: CreateLendState,
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .verticalScroll(rememberScrollState())
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    SectionLabel("Item")
    ItemPicker(items = state.items, selected = state.selectedItem) {
      state.dispatch(CreateLendEvents.SelectItem(it))
    }

    TextField(
      state = state.quantity,
      label = { Text("Quantity") },
      keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
      modifier = Modifier.fillMaxWidth(),
    )

    HorizontalDivider()

    SectionLabel("Recipient")
    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
      LendMode.entries.forEachIndexed { index, mode ->
        SegmentedButton(
          selected = state.mode == mode,
          onClick = { state.dispatch(CreateLendEvents.SelectMode(mode)) },
          shape = SegmentedButtonDefaults.itemShape(index, LendMode.entries.size),
          label = { Text(mode.label()) },
        )
      }
    }

    when (state.mode) {
      LendMode.GUEST -> GuestFields(state)
      LendMode.GROUP_MEMBER -> GroupMemberFields(state)
    }

    Spacer(Modifier.height(8.dp))

    Button(
      onClick = { state.dispatch(CreateLendEvents.AttemptSave) },
      enabled = state.canSubmit,
      modifier = Modifier.fillMaxWidth(),
    ) {
      Text(if (state.submitting) "Saving…" else "Save")
    }
  }
}

@Composable
private fun GuestFields(state: CreateLendState) {
  TextField(
    state = state.firstName,
    label = { Text("First name") },
    modifier = Modifier.fillMaxWidth(),
  )
  TextField(
    state = state.lastName,
    label = { Text("Last name") },
    modifier = Modifier.fillMaxWidth(),
  )
}

@Composable
private fun GroupMemberFields(state: CreateLendState) {
  SectionLabel("Group")
  GroupPicker(groups = state.groups, selected = state.selectedGroup) {
    state.dispatch(CreateLendEvents.SelectGroup(it))
  }

  if (state.selectedGroup != null) {
    SectionLabel("Member")
    if (state.members.isEmpty()) {
      Text("No other members in this group.", style = MaterialTheme.typography.bodySmall)
    } else {
      MemberPicker(members = state.members, selected = state.selectedMember) {
        state.dispatch(CreateLendEvents.SelectMember(it))
      }
    }
  }
}

@Composable
private fun ItemPicker(
  items: List<ClientItem>,
  selected: ClientItem?,
  onSelect: (ClientItem) -> Unit,
) {
  if (items.isEmpty()) {
    Text("No items yet — create one first.", style = MaterialTheme.typography.bodySmall)
    return
  }
  Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
    items.forEach { item ->
      PickerRow(
        text = item.name,
        selected = item.id == selected?.id,
        onClick = { onSelect(item) },
      )
    }
  }
}

@Composable
private fun GroupPicker(
  groups: List<ClientGroup>,
  selected: ClientGroup?,
  onSelect: (ClientGroup) -> Unit,
) {
  if (groups.isEmpty()) {
    Text("You aren't in any groups yet.", style = MaterialTheme.typography.bodySmall)
    return
  }
  Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
    groups.forEach { group ->
      PickerRow(
        text = group.name,
        selected = group.id == selected?.id,
        onClick = { onSelect(group) },
      )
    }
  }
}

@Composable
private fun MemberPicker(
  members: List<ClientProfile>,
  selected: ClientProfile?,
  onSelect: (ClientProfile) -> Unit,
) {
  Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
    members.forEach { member ->
      PickerRow(
        text = "${member.firstName} ${member.lastName}",
        selected = member.id == selected?.id,
        onClick = { onSelect(member) },
      )
    }
  }
}

@Composable
private fun PickerRow(
  text: String,
  selected: Boolean,
  onClick: () -> Unit,
) {
  val bg = if (selected) MaterialTheme.colorScheme.primaryContainer
  else MaterialTheme.colorScheme.surfaceVariant
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(bg)
      .clickable(onClick = onClick)
      .padding(12.dp),
  ) {
    Text(text)
  }
}

@Composable
private fun SectionLabel(text: String) {
  Text(text, style = MaterialTheme.typography.labelLarge)
}

private fun LendMode.label(): String = when (this) {
  LendMode.GUEST -> "Guest"
  LendMode.GROUP_MEMBER -> "Group member"
}
