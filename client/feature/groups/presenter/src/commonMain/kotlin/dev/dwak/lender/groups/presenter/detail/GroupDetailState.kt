package dev.dwak.lender.groups.presenter.detail

import com.slack.circuit.runtime.CircuitUiState
import dev.dwak.lender.lender_app.Loadable
import dev.dwak.lender.lender_app.Refreshable
import dev.dwak.models.client.ClientGroupDetail
import dev.dwak.models.client.ClientMembership
import dev.dwak.models.client.ClientMembershipStatus

data class GroupDetailState(
  override val loading: Boolean,
  override val refreshing: Boolean,
  val detail: ClientGroupDetail?,
  val currentUserMembership: ClientMembership?,
  val dispatch: (GroupDetailEvents) -> Unit,
) : CircuitUiState, Loadable, Refreshable
