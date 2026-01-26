package dev.dwak.lender.repos.server

import dev.dwak.lender.models.server.ServerGroup
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerItem
import dev.dwak.lender.models.server.ServerItemId
import dev.dwak.lender.models.server.ServerProfileId

interface GroupsRepo {
  suspend fun listAll(): List<ServerGroup>

  suspend fun groupsForProfile(id: ServerProfileId): List<ServerGroup>

  suspend fun groupsForItem(id: ServerItemId): List<ServerGroup>

  suspend fun groupById(id: ServerGroupId): ServerGroup?
}