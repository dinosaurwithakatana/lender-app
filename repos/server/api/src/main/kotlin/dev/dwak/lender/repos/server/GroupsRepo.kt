package dev.dwak.lender.repos.server

import dev.dwak.lender.models.server.ServerGroup
import dev.dwak.lender.models.server.ServerProfileId

interface GroupsRepo {
  suspend fun listAll(): List<ServerGroup>
  suspend fun groupsForProfile(id: ServerProfileId): List<ServerGroup>
}