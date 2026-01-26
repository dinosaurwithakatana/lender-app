package dev.dwak.lender.repos.server

import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerProfileId

interface GroupMembershipRepo {
  suspend fun isProfileInGroup(profile: ServerProfileId, group: ServerGroupId): Boolean
}