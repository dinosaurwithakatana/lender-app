package dev.dwak.lender.repos.server

import dev.dwak.lender.db.DbGroup
import dev.dwak.lender.db.DbGroupMembership
import dev.dwak.lender.db.DbGroupMembershipStatus
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.GroupMembershipQueries
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerGroupMembership
import dev.dwak.lender.models.server.ServerGroupMembershipId
import dev.dwak.lender.models.server.ServerGroupMembershipStatus
import dev.dwak.lender.models.server.ServerGroupMembershipWithProfile
import dev.dwak.lender.models.server.ServerProfile
import dev.dwak.lender.models.server.ServerProfileId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

@ContributesBinding(AppScope::class)
class RealGroupMembershipRepo(
  private val membershipQueries: GroupMembershipQueries,
) : GroupMembershipRepo {
  override suspend fun isProfileInGroup(
    profile: ServerProfileId,
    group: ServerGroupId
  ): Boolean {
    return membershipQueries.isMemberOfGroup(
      profile_id = DbProfile.Id(profile.id),
      group_id = DbGroup.Id(group.id)
    ).executeAsOne()
  }

  override suspend fun isOwnerForGroup(
    profile: ServerProfileId,
    group: ServerGroupId
  ): Boolean {
    return membershipQueries.isOwnerForGroup(
      profile_id = DbProfile.Id(profile.id),
      group_id = DbGroup.Id(group.id)
    ).executeAsOne()
  }

  override suspend fun profilesInGroup(group: ServerGroupId): List<ServerProfile> {
    return membershipQueries.profilesInGroup(
      group_id = DbGroup.Id(group.id)
    ) { id, _, first_name, last_name ->
      ServerProfile(
        id = ServerProfileId(id.id),
        firstName = first_name,
        lastName = last_name,
      )
    }.executeAsList()
  }

  override suspend fun membershipById(id: ServerGroupMembershipId): ServerGroupMembership? {
    return membershipQueries.selectById(DbGroupMembership.Id(id.id)) { row_id, profile_id, group_id, status, created_at ->
      ServerGroupMembership(
        id = ServerGroupMembershipId(row_id.id),
        profileId = ServerProfileId(profile_id.id),
        groupId = ServerGroupId(group_id.id),
        status = status.toServer(),
        createdAt = created_at,
      )
    }.executeAsOneOrNull()
  }

  override suspend fun membershipsForGroup(
    group: ServerGroupId
  ): List<ServerGroupMembershipWithProfile> {
    return membershipQueries.membershipsForGroup(
      group_id = DbGroup.Id(group.id)
    ) { id, profile_id, group_id, status, created_at, first_name, last_name ->
      ServerGroupMembershipWithProfile(
        membership = ServerGroupMembership(
          id = ServerGroupMembershipId(id.id),
          profileId = ServerProfileId(profile_id.id),
          groupId = ServerGroupId(group_id.id),
          status = status.toServer(),
          createdAt = created_at,
        ),
        profile = ServerProfile(
          id = ServerProfileId(profile_id.id),
          firstName = first_name,
          lastName = last_name,
        ),
      )
    }.executeAsList()
  }
}

private fun DbGroupMembershipStatus.toServer(): ServerGroupMembershipStatus = when (this) {
  DbGroupMembershipStatus.APPROVED -> ServerGroupMembershipStatus.APPROVED
  DbGroupMembershipStatus.REQUESTED -> ServerGroupMembershipStatus.REQUESTED
  DbGroupMembershipStatus.OWNER -> ServerGroupMembershipStatus.OWNER
}
