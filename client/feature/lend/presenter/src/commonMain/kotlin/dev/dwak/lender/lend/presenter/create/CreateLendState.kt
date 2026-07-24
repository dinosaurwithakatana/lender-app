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
  val quantityError: String?
    get() {
      val raw = quantity.text.toString()
      if (raw.isEmpty()) return null
      val qty = raw.toIntOrNull() ?: return "Must be a whole number"
      if (qty <= 0) return "Must be at least 1"
      val item = selectedItem ?: return null
      if (qty > item.availableQuantity) return "Only ${item.availableQuantity} available"
      return null
    }

  val canSubmit: Boolean
    get() {
      if (submitting) return false
      selectedItem ?: return false
      val qty = quantity.text.toString().toIntOrNull() ?: return false
      if (qty <= 0 || quantityError != null) return false
      return when (mode) {
        LendMode.GUEST -> firstName.text.isNotBlank() && lastName.text.isNotBlank()
        LendMode.GROUP_MEMBER -> selectedGroup != null && selectedMember != null
      }
    }
}
