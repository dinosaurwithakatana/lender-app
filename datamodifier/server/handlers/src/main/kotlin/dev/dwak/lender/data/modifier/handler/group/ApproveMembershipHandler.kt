package dev.dwak.lender.data.modifier.handler.group

import dev.dwak.lender.data.modification.group.ApproveMembershipMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.db.DbGroupMembership
import dev.dwak.lender.db.DbGroupMembershipStatus
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.GroupMembershipQueries
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(ApproveMembershipMod::class)
class ApproveMembershipHandler(
  private val membershipQueries: GroupMembershipQueries,
) : DataModification.Handler<ApproveMembershipMod.Result, ApproveMembershipMod> {
  override suspend fun handle(mod: ApproveMembershipMod): ApproveMembershipMod.Result {
    val membership = membershipQueries.selectById(DbGroupMembership.Id(mod.membershipId.id))
      .executeAsOneOrNull()
      ?: return ApproveMembershipMod.Result.NotFound

    val isOwner = membershipQueries.isOwnerForGroup(
      profile_id = DbProfile.Id(mod.actingProfileId.id),
      group_id = membership.group_id,
    ).executeAsOne()
    if (!isOwner) return ApproveMembershipMod.Result.Unauthorized

    membershipQueries.updateStatus(
      status = DbGroupMembershipStatus.APPROVED,
      id = membership.id,
    ).await()
    return ApproveMembershipMod.Result.Success
  }
}
