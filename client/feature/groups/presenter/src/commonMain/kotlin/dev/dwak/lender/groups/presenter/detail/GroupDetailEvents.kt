package dev.dwak.lender.groups.presenter.detail

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface GroupDetailEvents : CircuitUiEvent {
  data object Back : GroupDetailEvents
  data object Refresh : GroupDetailEvents
}
