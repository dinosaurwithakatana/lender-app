package dev.dwak.lender.groups.presenter.addMember

import androidx.compose.foundation.text.input.TextFieldState
import com.slack.circuit.runtime.CircuitUiState
import dev.dwak.models.client.ClientProfile

data class AddMemberState(
  val email: TextFieldState,
  val lookup: LookupResult,
  val submitting: Boolean,
  val errorMessage: String?,
  val dispatch: (AddMemberEvents) -> Unit,
) : CircuitUiState {
  sealed interface LookupResult {
    data object Idle : LookupResult
    data object Searching : LookupResult
    data object NotFound : LookupResult
    data class Found(val profile: ClientProfile) : LookupResult
  }
}
