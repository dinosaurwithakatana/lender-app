package dev.dwak.lender.repos.server

import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerGroupMembership
import dev.dwak.lender.models.server.ServerGroupMembershipId
import dev.dwak.lender.models.server.ServerGroupMembershipWithProfile
import dev.dwak.lender.models.server.ServerProfile
import dev.dwak.lender.models.server.ServerProfileId

interface GroupMembershipRepo {
  suspend fun isProfileInGroup(profile: ServerProfileId, group: ServerGroupId): Boolean

  suspend fun isOwnerForGroup(profile: ServerProfileId, group: ServerGroupId): Boolean

  suspend fun profilesInGroup(group: ServerGroupId): List<ServerProfile>

  suspend fun membershipById(id: ServerGroupMembershipId): ServerGroupMembership?

  suspend fun membershipsForGroup(group: ServerGroupId): List<ServerGroupMembershipWithProfile>
}
