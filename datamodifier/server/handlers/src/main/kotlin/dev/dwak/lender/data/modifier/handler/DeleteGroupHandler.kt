package dev.dwak.lender.data.modifier.handler

import dev.dwak.lender.data.modification.DeleteGroup
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.db.DbGroup
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.GroupMembershipQueries
import dev.dwak.lender.db.GroupQueries
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding

@ContributesIntoMap(
  scope = AppScope::class,
  binding = binding<@ModificationKey(DeleteGroup::class) BoundHandler>()
)
class DeleteGroupHandler(
  private val groupQueries: GroupQueries,
  private val groupMembershipQueries: GroupMembershipQueries,
) : DataModification.Handler<DeleteGroup.Result, DeleteGroup> {
  override suspend fun handle(mod: DeleteGroup): DeleteGroup.Result {
    val id = DbGroup.Id(mod.id.id)
    val isOwner = groupMembershipQueries.isOwnerForGroup(
      profile_id = DbProfile.Id(mod.requestingProfileId.id),
      group_id = DbGroup.Id(mod.id.id)
    ).executeAsOne()

    if (isOwner) {
      groupQueries.delete(id).await()
      return DeleteGroup.Result.Success
    } else {
      return DeleteGroup.Result.Unauthorized
    }
  }
}