package dev.dwak.lender.groups.presenter.detail

import com.slack.circuit.runtime.CircuitUiState
import dev.dwak.lender.lender_app.Loadable
import dev.dwak.lender.lender_app.Refreshable
import dev.dwak.models.client.ClientGroupDetail

data class GroupDetailState(
  val detail: ClientGroupDetail?,
  override val loading: Boolean,
  override val refreshing: Boolean,
  val dispatch: (GroupDetailEvents) -> Unit,
) : CircuitUiState, Loadable, Refreshable
