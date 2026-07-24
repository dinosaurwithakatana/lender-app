package dev.dwak.lender.lend.presenter.home

import com.slack.circuit.runtime.CircuitUiEvent
import dev.dwak.models.client.ClientLend
import dev.dwak.models.client.ClientLendStatus

sealed interface LendHomeEvents : CircuitUiEvent {
  data object AddLend : LendHomeEvents
  data object Refresh : LendHomeEvents
  data class DeleteLend(val lend: ClientLend) : LendHomeEvents
  data class AdvanceLend(val lend: ClientLend) : LendHomeEvents
}

enum class LendActionKind { DENY, RETURN, DELETE }

fun ClientLendStatus.actionKind(): LendActionKind = when (this) {
  ClientLendStatus.REQUESTED, ClientLendStatus.APPROVED -> LendActionKind.DENY
  ClientLendStatus.LENT -> LendActionKind.RETURN
  ClientLendStatus.DENIED, ClientLendStatus.RETURNED -> LendActionKind.DELETE
}

enum class LendAdvanceKind { APPROVE, MARK_LENT }

fun ClientLend.advanceKind(): LendAdvanceKind? {
  if (direction != ClientLend.Direction.OUTGOING) return null
  return when (status) {
    ClientLendStatus.REQUESTED -> LendAdvanceKind.APPROVE
    ClientLendStatus.APPROVED -> LendAdvanceKind.MARK_LENT
    ClientLendStatus.LENT,
    ClientLendStatus.DENIED,
    ClientLendStatus.RETURNED -> null
  }
}
