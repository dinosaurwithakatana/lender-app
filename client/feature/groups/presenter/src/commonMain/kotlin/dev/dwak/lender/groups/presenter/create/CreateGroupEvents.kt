package dev.dwak.lender.groups.presenter.create

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface CreateGroupEvents : CircuitUiEvent {
  data object Back : CreateGroupEvents

  data object AttemptSave : CreateGroupEvents
}
