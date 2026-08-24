package dev.dwak.lender.groups.presenter.addMember

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface AddMemberEvents : CircuitUiEvent {
  data object Back : AddMemberEvents
  data object Search : AddMemberEvents
  data object Invite : AddMemberEvents
}
