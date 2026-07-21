package dev.dwak.lender.repos.client

import dev.dwak.models.client.ClientItem
import kotlinx.coroutines.flow.Flow

interface ItemRepo {
  val items: Flow<List<ClientItem>>

  sealed interface RefreshTypes: RepoRefresher.RefreshType {
    data object AllItems: RefreshTypes
  }
}