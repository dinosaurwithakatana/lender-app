package dev.dwak.lender.groups.presenter.home

import com.slack.circuit.runtime.CircuitUiState
import dev.dwak.lender.lender_app.Loadable
import dev.dwak.lender.lender_app.Refreshable
import dev.dwak.models.client.ClientGroup

data class GroupsHomeState(
  val groups: List<ClientGroup> = emptyList(),
  override val loading: Boolean,
  override val refreshing: Boolean,
  val dispatch: (GroupsHomeEvents) -> Unit,
): CircuitUiState, Loadable, Refreshable
