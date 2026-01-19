package dev.dwak.lender.repos.server

import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerProfile
import dev.dwak.lender.models.server.ServerUserId

interface ProfileRepo {
  suspend fun getByEmail(email: String): ServerProfile

  suspend fun listProfiles(): List<ServerProfile>

  suspend fun getByUserId(userId: ServerUserId): ServerProfile

  suspend fun getProfilesInGroup(id: ServerGroupId): List<ServerProfile>
}