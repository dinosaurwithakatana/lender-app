package dev.dwak.lender.data.modifier.handler.group

import dev.dwak.lender.data.modification.group.RemoveMembershipMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.db.DbGroupMembership
import dev.dwak.lender.db.DbGroupMembershipStatus
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.GroupMembershipQueries
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(RemoveMembershipMod::class)
class RemoveMembershipHandler(
  private val membershipQueries: GroupMembershipQueries,
) : DataModification.Handler<RemoveMembershipMod.Result, RemoveMembershipMod> {
  override suspend fun handle(mod: RemoveMembershipMod): RemoveMembershipMod.Result {
    val membership = membershipQueries.selectById(DbGroupMembership.Id(mod.membershipId.id))
      .executeAsOneOrNull()
      ?: return RemoveMembershipMod.Result.NotFound

    if (membership.status == DbGroupMembershipStatus.OWNER) {
      return RemoveMembershipMod.Result.Unauthorized
    }

    val isSelf = membership.profile_id.id == mod.actingProfileId.id
    val isOwner = membershipQueries.isOwnerForGroup(
      profile_id = DbProfile.Id(mod.actingProfileId.id),
      group_id = membership.group_id,
    ).executeAsOne()

    if (!isSelf && !isOwner) return RemoveMembershipMod.Result.Unauthorized

    membershipQueries.deleteById(membership.id).await()
    return RemoveMembershipMod.Result.Success
  }
}
