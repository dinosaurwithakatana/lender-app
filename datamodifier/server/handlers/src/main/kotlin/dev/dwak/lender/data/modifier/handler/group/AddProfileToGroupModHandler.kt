package dev.dwak.lender.data.modifier.handler.group

import dev.dwak.lender.data.modification.group.AddProfileToGroupMod
import dev.dwak.lender.data.modification.group.CreateGroupMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.BoundHandler
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.data.modifier.handler.toDb
import dev.dwak.lender.db.DbGroupMembership
import dev.dwak.lender.db.DbGroupMembershipStatus
import dev.dwak.lender.db.GroupMembershipQueries
import dev.dwak.lender.models.server.ServerGroupMembershipStatus
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@ContributesIntoMap(
  scope = AppScope::class,
  binding = binding<@ModificationKey(AddProfileToGroupMod::class) BoundHandler>()
)
class AddProfileToGroupModHandler(
  private val groupMembershipQueries: GroupMembershipQueries,
) : DataModification.Handler<AddProfileToGroupMod.Result, AddProfileToGroupMod>{
  @OptIn(ExperimentalUuidApi::class)
  override suspend fun handle(mod: AddProfileToGroupMod): AddProfileToGroupMod.Result {
    val membershipId = DbGroupMembership.Id(Uuid.generateV4().toString())
    groupMembershipQueries.insert(
      dbGroupMembership = DbGroupMembership(
        id = membershipId,
        profile_id = mod.profileId.toDb(),
        group_id = mod.groupId.toDb(),
        status = when (mod.status) {
          ServerGroupMembershipStatus.APPROVED -> DbGroupMembershipStatus.APPROVED
          ServerGroupMembershipStatus.REQUESTED -> DbGroupMembershipStatus.REQUESTED
          ServerGroupMembershipStatus.OWNER -> DbGroupMembershipStatus.OWNER
        },
        created_at = Clock.System.now()
      )
    ).await()
    return AddProfileToGroupMod.Result.Success
  }
}