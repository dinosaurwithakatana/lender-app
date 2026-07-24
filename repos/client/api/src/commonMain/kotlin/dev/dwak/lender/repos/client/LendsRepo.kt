package dev.dwak.lender.repos.client

import dev.dwak.models.client.ClientLend
import kotlinx.coroutines.flow.Flow

interface LendsRepo {
  val currentUserLends: Flow<List<ClientLend>>

  sealed interface RefreshTypes : RepoRefresher.RefreshType {
    data object CurrentUserLends : RefreshTypes
  }
}
