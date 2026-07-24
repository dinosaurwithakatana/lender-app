package dev.dwak.lender.lend.presenter.create

import androidx.compose.foundation.text.input.TextFieldState
import com.slack.circuit.runtime.CircuitUiState
import dev.dwak.models.client.ClientGroup
import dev.dwak.models.client.ClientItem
import dev.dwak.models.client.ClientProfile

data class CreateLendState(
  val mode: LendMode,
  val items: List<ClientItem>,
  val groups: List<ClientGroup>,
  val members: List<ClientProfile>,
  val selectedItem: ClientItem?,
  val selectedGroup: ClientGroup?,
  val selectedMember: ClientProfile?,
  val firstName: TextFieldState,
  val lastName: TextFieldState,
  val quantity: TextFieldState,
  val submitting: Boolean,
  val dispatch: (CreateLendEvents) -> Unit,
) : CircuitUiState {
  val canSubmit: Boolean
    get() = when {
      submitting -> false
      selectedItem == null -> false
      quantity.text.toString().toIntOrNull()?.let { it > 0 } != true -> false
      mode == LendMode.GUEST -> firstName.text.isNotBlank() && lastName.text.isNotBlank()
      mode == LendMode.GROUP_MEMBER -> selectedGroup != null && selectedMember != null
      else -> false
    }
}
