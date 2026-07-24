package dev.dwak.lender.groups.presenter.home

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface GroupsHomeEvents : CircuitUiEvent {
  data object CreateGroup : GroupsHomeEvents
}