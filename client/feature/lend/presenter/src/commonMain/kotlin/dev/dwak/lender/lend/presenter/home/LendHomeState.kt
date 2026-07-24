package dev.dwak.lender.lend.presenter.home

import com.slack.circuit.runtime.CircuitUiState
import dev.dwak.lender.lender_app.Loadable
import dev.dwak.lender.lender_app.Refreshable
import dev.dwak.models.client.ClientLend

data class LendHomeState(
  val lends: List<ClientLend> = emptyList(),
  override val loading: Boolean,
  override val refreshing: Boolean,
  val dispatch: (LendHomeEvents) -> Unit,
) : CircuitUiState, Loadable, Refreshable
