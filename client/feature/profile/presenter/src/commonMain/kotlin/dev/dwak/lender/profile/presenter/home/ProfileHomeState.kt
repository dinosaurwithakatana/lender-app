package dev.dwak.lender.profile.presenter.home

import com.slack.circuit.runtime.CircuitUiState
import dev.dwak.lender.lender_app.Loadable
import dev.dwak.lender.lender_app.Refreshable
import dev.dwak.models.client.ClientProfile

data class ProfileHomeState(
  val profile: ClientProfile?,
  override val loading: Boolean,
  override val refreshing: Boolean,
  val dispatch: (ProfileHomeEvents) -> Unit,
) : CircuitUiState, Loadable, Refreshable
