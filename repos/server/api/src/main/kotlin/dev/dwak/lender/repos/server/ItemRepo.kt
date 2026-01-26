package dev.dwak.lender.repos.server

import dev.dwak.lender.models.server.ServerItem
import dev.dwak.lender.models.server.ServerItemId
import dev.dwak.lender.models.server.ServerProfileId

interface ItemRepo {
  suspend fun getItemsForProfile(id: ServerProfileId): List<ServerItem>

  suspend fun getItemById(id: ServerItemId): ServerItem?
}