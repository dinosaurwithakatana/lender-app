package dev.dwak.lender.groups.presenter.home

import com.slack.circuit.runtime.CircuitUiState

data class GroupsHomeState(
  val dispatch: (GroupsHomeEvents) -> Unit,
): CircuitUiState
