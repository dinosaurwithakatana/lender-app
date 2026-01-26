package dev.dwak.lender.repos.server

import dev.dwak.lender.db.DbGroup
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.GroupMembershipQueries
import dev.dwak.lender.models.server.ServerGroupId
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
}