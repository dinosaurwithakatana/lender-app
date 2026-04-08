package dev.dwak.lender.data.modifier.handler.group

import dev.dwak.lender.data.modification.group.CreateGroupMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.db.DbGroup
import dev.dwak.lender.db.DbGroupMembership
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.GroupQueries
import dev.dwak.lender.models.server.ServerGroupId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import java.util.UUID
import kotlin.time.Clock

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(CreateGroupMod::class)
class CreateGroupHandler(
  private val groupQueries: GroupQueries
) : DataModification.Handler<CreateGroupMod.Result, CreateGroupMod> {
  override suspend fun handle(mod: CreateGroupMod): CreateGroupMod.Result {
    val groupId = DbGroup.Id(UUID.randomUUID().toString())
    groupQueries.createGroupAndAddCreator(
      group_id = groupId,
      group_name = mod.name,
      membership_id = DbGroupMembership.Id(UUID.randomUUID().toString()),
      profile_id = DbProfile.Id(mod.owner.id),
      created_at = Clock.System.now()
    )

    return CreateGroupMod.Result.Success(ServerGroupId(id = groupId.id))
  }
}