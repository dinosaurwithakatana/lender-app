package dev.dwak.lender.repos.client

import dev.dwak.models.client.ClientItem
import kotlinx.coroutines.flow.Flow

interface ItemRepo: RefreshableRepo<ItemRepo.ItemRefreshers> {
  val items: Flow<List<ClientItem>>

  sealed interface ItemRefreshers: RefreshableRepo.RefreshItem {
    data object AllItems: ItemRefreshers
  }
}