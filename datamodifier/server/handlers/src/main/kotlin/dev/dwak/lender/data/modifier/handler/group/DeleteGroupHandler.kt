package dev.dwak.lender.data.modifier.handler.group

import dev.dwak.lender.data.modification.group.DeleteGroupMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.BoundHandler
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.db.DbGroup
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.GroupMembershipQueries
import dev.dwak.lender.db.GroupQueries
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding

@ContributesIntoMap(
  scope = AppScope::class,
  binding = binding<@ModificationKey(DeleteGroupMod::class) BoundHandler>()
)
class DeleteGroupHandler(
  private val groupQueries: GroupQueries,
  private val groupMembershipQueries: GroupMembershipQueries,
) : DataModification.Handler<DeleteGroupMod.Result, DeleteGroupMod> {
  override suspend fun handle(mod: DeleteGroupMod): DeleteGroupMod.Result {
    val id = DbGroup.Id(mod.id.id)
    val isOwner = groupMembershipQueries.isOwnerForGroup(
      profile_id = DbProfile.Id(mod.requestingProfileId.id),
      group_id = DbGroup.Id(mod.id.id)
    ).executeAsOne()

    if (isOwner) {
      groupQueries.delete(id).await()
      return DeleteGroupMod.Result.Success
    } else {
      return DeleteGroupMod.Result.Unauthorized
    }
  }
}