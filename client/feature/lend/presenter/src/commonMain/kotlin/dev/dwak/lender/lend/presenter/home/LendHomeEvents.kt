package dev.dwak.lender.lend.presenter.home

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface LendHomeEvents : CircuitUiEvent {
  data object AddLend : LendHomeEvents
  data object Refresh : LendHomeEvents
}
