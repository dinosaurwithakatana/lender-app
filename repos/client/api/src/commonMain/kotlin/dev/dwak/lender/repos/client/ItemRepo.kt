package dev.dwak.lender.repos.client

import dev.dwak.models.client.ClientItem
import kotlinx.coroutines.flow.Flow

interface ItemRepo {
  suspend fun items(): List<ClientItem>
}